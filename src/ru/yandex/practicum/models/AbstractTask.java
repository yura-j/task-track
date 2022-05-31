package ru.yandex.practicum.models;

import ru.yandex.practicum.exceptions.TimeTableBusyException;
import ru.yandex.practicum.managers.TaskStore;
import ru.yandex.practicum.managers.TimeTable;
import ru.yandex.practicum.util.Managers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract public class AbstractTask implements Comparable<AbstractTask> {

    protected int id = 0;
    protected String name = "";
    protected String description = "";
    protected TaskStatus status = TaskStatus.NEW;
    protected TaskType type;
    protected int duration = 0;
    protected LocalDateTime startTime;

    protected TaskStore store = Managers.getDefaultStore();

    public AbstractTask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    //Сериализация
    public AbstractTask(CompressedTaskDto dto) {
        this.id = dto.id;
        this.name = dto.name;
        this.description = dto.description;
        this.status = dto.status;
        this.duration = dto.duration;
        this.startTime = dto.startTime;
    }

    public CompressedTaskDto toCompressedTaskDto() {
        CompressedTaskDto dto = new CompressedTaskDto();
        dto.id = this.id;
        dto.name = this.name;
        dto.description = this.description;
        dto.status = this.status;
        dto.type = this.type;
        dto.epicId = this.getEpicId();
        dto.startTime = this.extractStartTime();
        dto.duration = this.extractDuration();
        return dto;
    }
    protected Integer getEpicId() {
        return 0;
    }
    protected LocalDateTime extractStartTime() {
        return startTime;
    }
    protected int extractDuration() {
        return duration;
    }

    //Методы поддерджки расписания
    public LocalDateTime getEndTime() {
        if (startTime != null) {
            return startTime.plusMinutes(duration);
        }
        return null;
    }

    public void addToTimeTable(TimeTable table) {
        if (startTime == null) {
            return;
        }
        if (!table.isIntervalFreeInTable(startTime, duration)) {
            throw new TimeTableBusyException("Задача " + this + " пересекается по времени выполнения с другими задачами");
        }
        table.holdInterval(startTime, duration);
    }

    public void removeFromTimeTable(TimeTable table) {
        table.freeInterval(startTime, duration);
    }


    //Круд операции
    public AbstractTask add() {
        this.id = store.generateNewId();
        this.store.putTask(this);
        return this;
    }

    public AbstractTask update() {
        store.putTask(this);
        return this;
    }

    public List<AbstractTask> remove() {
        store.removeTask(this.id);
        return new ArrayList<>();
    }

    //Геттеры, Сеттеры
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }


    //Методы стандартной библиотеки
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + id + "@" + status;
    }

    @Override
    public int compareTo(AbstractTask comparingTask) {
        if (startTime == null) {
            return +1;
        }
        if (comparingTask.startTime == null) {
            return -1;
        }
        return comparingTask.startTime.compareTo(startTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractTask that = (AbstractTask) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
