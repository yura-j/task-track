package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.util.LimitedStackLinkedList;

import java.util.List;

public class InMemoryTaskHistory implements TaskHistoryManager {
    public static final int TASK_LIMIT = 10;
    LimitedStackLinkedList<AbstractTask> stack = new LimitedStackLinkedList<>(TASK_LIMIT);

    public List<AbstractTask> getHistory() {
        return stack;
    }

    public void update(AbstractTask task) {
        this.stack.add(task);
    }
}
