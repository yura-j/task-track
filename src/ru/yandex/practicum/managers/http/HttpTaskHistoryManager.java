package ru.yandex.practicum.managers.http;

import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.managers.TaskStore;
import ru.yandex.practicum.managers.in_memory.InMemoryTaskHistoryManager;
import ru.yandex.practicum.util.Json;
import ru.yandex.practicum.util.Managers;

import java.util.ArrayList;

public class HttpTaskHistoryManager extends InMemoryTaskHistoryManager {

    TaskStore store = Managers.getDefaultStore();

    public String toJson() {
        String[] ids = super
                .getHistory()
                .stream()
                .map(task -> String.valueOf(task.getId()))
                .toArray(String[]::new);
        return Json.build().to(ids);
    }

    public void fromJson(String jsonHistory) {
        if (jsonHistory.isBlank()){
            return;
        }
        ArrayList<Integer> ids = Json.build().from(jsonHistory, new TypeToken<ArrayList<Integer>>(){}.getType());
            ids
                .stream()
                .map(store::getTask)
                .forEach(this::add);
    }
}
