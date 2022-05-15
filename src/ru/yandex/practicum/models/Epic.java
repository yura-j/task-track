package ru.yandex.practicum.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

final public class Epic extends AbstractTask {
    private final HashMap<Integer, SubTask> subTasks = new HashMap<>();

    public Epic(String name, String description) {
        super(name, description);
        type = TaskType.EPIC;
    }

    public Epic(CompressedTaskDto dto) {
        super(dto);
        type = TaskType.EPIC;
    }


    @Override
    public void setStatus(TaskStatus status) {
        System.out.println("Аяяй. Низзя менять статус эпика, он компьютед");
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

    public void addSubtask(SubTask task) {
        subTasks.put(task.id, task);
        this.evaluateStatus();
    }

    public void removeSubtask(SubTask task) {
        subTasks.remove(task.id);
        this.evaluateStatus();
    }

    public List<AbstractTask> remove() {
        List<AbstractTask> subtaskForRemove = new ArrayList<>();
        subtaskForRemove.addAll(subTasks.values());
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
