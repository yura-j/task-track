package ru.yandex.practicum.models;

import ru.yandex.practicum.managers.TaskStore;
import ru.yandex.practicum.util.Managers;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractTask {
    protected int id = 0;
    protected String name = "";
    protected String description = "";
    protected TaskStatus status = TaskStatus.NEW;
    public TaskType type;
    protected TaskStore store = Managers.getDefaultStore();


    public AbstractTask() {

    }

    protected Integer getEpicId() {
        return 0;
    }

    public AbstractTask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public AbstractTask(CompressedTaskDto dto) {
        this.id = dto.id;
        this.name = dto.name;
        this.description = dto.description;
        this.status = dto.status;
    }

    public CompressedTaskDto toCompressedTaskDto() {
        CompressedTaskDto dto = new CompressedTaskDto();
        dto.id = this.id;
        dto.name = this.name;
        dto.description = this.description;
        dto.status = this.status;
        dto.type = this.type;
        dto.epicId = this.getEpicId();
        return dto;
    }

    public void replicateMeTo(AbstractTask task) {
        task.id = id;
        task.name = name;
        task.description = description;
        task.status = status;
        task.store = store;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public AbstractTask add() {
        this.id = store.generateNewId();
        this.store.addTask(this);
        return this;
    }

    public AbstractTask update() {
        store.addTask(this);
        return this;
    }

    public List<AbstractTask> remove() {
        store.removeTask(this.id);
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + id + "@" + status;
    }

}
