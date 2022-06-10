package ru.yandex.practicum.models;

final public class Task extends AbstractTask {

    public Task(String name, String description) {
        super(name, description);
        type = TaskType.TASK;
    }

    public Task(TaskDto dto) {
        super(dto);
        type = TaskType.TASK;
    }
}
