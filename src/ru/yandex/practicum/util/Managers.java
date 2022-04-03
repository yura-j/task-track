package ru.yandex.practicum.util;

import ru.yandex.practicum.managers.*;

final public class Managers {
    private final static InMemoryTaskStore inMemoryTaskStoreInstance = new InMemoryTaskStore();
    private final static InMemoryTaskHistoryManager inMemoryTaskHistory = new InMemoryTaskHistoryManager();
    private final static TaskManager defaultTaskManagerInstance = new InMemoryTaskManager();
    private final static TaskHistoryManager defaultTaskHistory = inMemoryTaskHistory;
    private final static TaskStore defaultTaskStoreInstance = inMemoryTaskStoreInstance;

    public static TaskManager getDefaultTaskManager() {
        return defaultTaskManagerInstance;
    }

    public static TaskStore getDefaultStore() {
        return defaultTaskStoreInstance;
    }

    public static TaskHistoryManager getDefaultHistory() {
        return defaultTaskHistory;
    }


    public static InMemoryTaskStore getInMemoryStore() {
        return inMemoryTaskStoreInstance;
    }

    public static InMemoryTaskHistoryManager getInMemoryTaskHistory() {
        return inMemoryTaskHistory;
    }
}
