package ru.yandex.practicum.models;

import ru.yandex.practicum.managers.TimeTable;

import java.time.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final public class Epic extends AbstractTask {
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
        this.startTime = null;
        this.duration = 0;
        type = TaskType.EPIC;
    }

    public Epic(CompressedTaskDto dto) {
        super(dto);
        this.startTime = null;
        this.duration = 0;
        type = TaskType.EPIC;
    }

    @Override
    protected LocalDateTime extractStartTime() {
        return null;
    }

    @Override
    protected int extractDuration() {
        return 0;
    }

    @Override
    public void setStatus(TaskStatus status) {
        System.out.println("Аяяй. Низзя менять статус эпика, он компьютед");
    }

    @Override
    public void addToTimeTable(TimeTable table) {
    }

    @Override
    public void removeFromTimeTable(TimeTable table) {
    }

    @Override
    public Epic update() {
        Epic storedEpic = (Epic) store.getTask(this.id);
        storedEpic.getSubTasks().forEach(SubTask::remove);
        this.getSubTasks().forEach(SubTask::add);
        super.update();
        return this;
    }

    public void evaluateStatus() {
        boolean allTasksIsDone = true;
        boolean allTasksIsNew = true;
        for (SubTask task : subTasks.values()) {
            allTasksIsDone = allTasksIsDone && (task.status == TaskStatus.DONE);
            allTasksIsNew = allTasksIsNew && (task.status == TaskStatus.NEW);
        }
        if (allTasksIsDone) {
            this.status = TaskStatus.DONE;
        } else if (allTasksIsNew) {
            this.status = TaskStatus.NEW;
        } else {
            this.status = TaskStatus.IN_PROGRESS;
        }
    }

    public void computeSubTasksBasedFields() {
        evaluateStatus();
        computeDuration();
        computeStartTime();
    }

    public void computeDuration() {
        this.duration = subTasks
                .values()
                .stream()
                .mapToInt(SubTask::getDuration)
                .sum();
    }

    public void computeStartTime() {
        this.startTime = subTasks
                .values()
                .stream()
                .map(SubTask::getStartTime)
                .min(LocalDateTime::compareTo)
                .orElse(null);
    }

    public void addSubtask(SubTask task) {
        subTasks.put(task.id, task);
        computeSubTasksBasedFields();
    }

    public void removeSubtask(SubTask task) {
        subTasks.remove(task.id);
        computeSubTasksBasedFields();
    }

    public List<AbstractTask> remove() {
        List<AbstractTask> subtaskForRemove = new ArrayList<>(subTasks.values());
        store.removeTask(this.id);
        return subtaskForRemove;
    }

    @Override
    public String toString() {
        return super.toString() + subTasks;
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }
}
