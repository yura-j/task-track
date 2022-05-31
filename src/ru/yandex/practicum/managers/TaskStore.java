package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.AbstractTask;

import java.util.ArrayList;
import java.util.Set;

public interface TaskStore {
    ArrayList<AbstractTask> getTasks();

    Set<AbstractTask> getPrioritizedTasks();

    AbstractTask getTask(Integer id);

    void putTask(AbstractTask task);

    void removeTask(int id);

    void removeTasks();

    int generateNewId();

}
