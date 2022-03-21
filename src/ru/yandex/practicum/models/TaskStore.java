package ru.yandex.practicum.models;

import java.util.ArrayList;
import java.util.HashMap;

final public class TaskStore {
    private HashMap<Integer, AbstractTask> taskList = new HashMap<>();
    private static TaskStore instance;
    private int maxId = 0;


    public ArrayList<AbstractTask> getTasks() {
        return new ArrayList<>(taskList.values());
    }

    public AbstractTask getTask(Integer id) {
        return taskList.get(id);
    }

    public void addTask(AbstractTask task) {
        taskList.put(task.getId(), task);
    }

    public void removeTask(int id) {
        taskList.remove(id);
    }

    public void removeTasks() {
        taskList.clear();
        maxId = 0;
    }

    public int generateNewId() {
        return (++maxId);
    }

    public static TaskStore getInstance() {
        if (instance == null) {
            instance = new TaskStore();
        }
        return instance;
    }
}
