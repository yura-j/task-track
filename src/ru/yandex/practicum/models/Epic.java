package ru.yandex.practicum.models;

import ru.yandex.practicum.managers.TimeTable;

import java.time.LocalDateTime;
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

    //Сериализация
    public Epic(TaskDto dto) {
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

    //Методы поддерджки расписания
    @Override
    public void addToTimeTable(TimeTable table) {
    }

    @Override
    public void removeFromTimeTable(TimeTable table) {
    }

    @Override
    public LocalDateTime getEndTime() {
        return subTasks
                .values()
                .stream()
                .map(SubTask::getEndTime)
                .max(LocalDateTime::compareTo)
                .orElse(null);
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

    //Хуки
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

    //Управление сабтасками
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void addSubtask(SubTask task) {
        subTasks.put(task.id, task);
        computeSubTasksBasedFields();
    }

    public void removeSubtask(SubTask task) {
        subTasks.remove(task.id);
        computeSubTasksBasedFields();
    }

    //Круд
    @Override
    public Epic update() {
        Epic storedEpic = (Epic) store.getTask(this.id);
        storedEpic.getSubTasks().forEach(SubTask::remove);
        this.getSubTasks().forEach(SubTask::add);
        super.update();
        return this;
    }

    public List<AbstractTask> remove() {
        List<AbstractTask> subtaskForRemove = new ArrayList<>(subTasks.values());
        store.removeTask(this.id);
        return subtaskForRemove;
    }

    //Методы стандартной библиотеки
    @Override
    public String toString() {
        return super.toString() + subTasks;
    }
}
