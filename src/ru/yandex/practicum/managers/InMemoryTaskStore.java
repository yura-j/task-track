package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.AbstractTask;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskStore implements TaskStore {
    protected final HashMap<Integer, AbstractTask> taskList = new HashMap<>();
    private static InMemoryTaskStore instance;
    protected int maxId = 0;

    protected HashMap<Integer, AbstractTask> getTaskList() {
        return taskList;
    }

    @Override
    public ArrayList<AbstractTask> getTasks() {
        return new ArrayList<>(taskList.values());
    }

    @Override
    public AbstractTask getTask(Integer id) {
        return taskList.get(id);
    }

    @Override
    public void addTask(AbstractTask task) {
        taskList.put(task.getId(), task);
    }

    @Override
    public void removeTask(int id) {
        taskList.remove(id);
    }

    @Override
    public void removeTasks() {
        taskList.clear();
        maxId = 0;
    }

    @Override
    public int generateNewId() {
        return (++maxId);
    }
}
