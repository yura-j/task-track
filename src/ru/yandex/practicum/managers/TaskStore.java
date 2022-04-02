package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.AbstractTask;

import java.util.ArrayList;

public interface TaskStore {
    ArrayList<AbstractTask> getTasks();

    AbstractTask getTask(Integer id);

    void addTask(AbstractTask task);

    void removeTask(int id);

    void removeTasks();

    int generateNewId();
}
