package ru.yandex.practicum.models;

import java.time.LocalDateTime;


public class TaskDto {
    public int id;
    public TaskType type;
    public String name;
    public TaskStatus status;
    public String description;
    public int epicId;
    public LocalDateTime startTime;
    public int duration;

    public String exportEpicIdToString() {
        return this.epicId == 0 ? "" : String.valueOf(epicId);
    }

    public String exportStartTimeToString() {
        return this.startTime == null ? "" : String.valueOf(startTime);
    }

    public String exportDurationToString() {
        return this.duration == 0 ? "" : String.valueOf(duration);
    }

}
