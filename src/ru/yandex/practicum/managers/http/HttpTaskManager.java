package ru.yandex.practicum.managers.http;

import ru.yandex.practicum.managers.in_memory.InMemoryTaskManager;
import ru.yandex.practicum.util.Managers;
import ru.yandex.practicum.util.Observer;

public class HttpTaskManager extends InMemoryTaskManager implements Observer {
    static final String STORE_KEY = "store";
    static final String HISTORY_KEY = "history";
    private final KVTaskClient handler;

    public static HttpTaskManager loadFromKVServer(String host) {
        HttpTaskStore store = Managers.getHttpTaskStore();
        HttpTaskHistoryManager history = Managers.getHttpTaskHistoryManager();

        HttpTaskManager manager = new HttpTaskManager(host, store, history);
        store.register(manager);
        return manager;
    }

    public void save() {
        handler.put(STORE_KEY, getStore().toJson());
        handler.put(HISTORY_KEY, getHistory().toJson());
    }

    public HttpTaskStore getStore() {
        return (HttpTaskStore) this.store;
    }

    public HttpTaskHistoryManager getHistory() {
        return (HttpTaskHistoryManager) this.history;
    }

    private HttpTaskManager(String host, HttpTaskStore store, HttpTaskHistoryManager history) {
        this.handler = new KVTaskClient(host);
        String storeJson = handler.load(STORE_KEY);
        String historyJson = handler.load(HISTORY_KEY);
        store.fromJson(storeJson);
        history.fromJson(historyJson);
        this.store = store;
        this.history = history;
    }

    @Override
    public void update() {
        save();
    }
}
