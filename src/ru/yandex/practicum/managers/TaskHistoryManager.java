package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.AbstractTask;

import java.util.List;

public interface TaskHistoryManager {

    List<AbstractTask> getHistory();

    void add(AbstractTask task);

    void remove(int id);
}
