package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.AbstractTask;

import java.util.LinkedList;
import java.util.List;

public class InMemoryTaskHistoryManager implements TaskHistoryManager {
    public static final int TASK_LIMIT = 10;
    LinkedList<AbstractTask> history = new LinkedList<>();

    public List<AbstractTask> getHistory() {
        return history;
    }

    public void update(AbstractTask task) {
        this.history.add(task);
        if (history.size() > TASK_LIMIT) {
            history.removeFirst();
        }
    }
}
