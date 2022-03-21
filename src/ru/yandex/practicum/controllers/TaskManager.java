package ru.yandex.practicum.controllers;

import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.models.Epic;
import ru.yandex.practicum.models.SubTask;
import ru.yandex.practicum.models.Task;
import ru.yandex.practicum.models.TaskStore;

import java.util.ArrayList;
import java.util.stream.Collectors;

final public class TaskManager {

    private final TaskStore store = TaskStore.getInstance();

    public ArrayList<Task> getTasks() {
        return store
                .getTasks()
                .stream()
                .filter(task -> task.getClass() == Task.class)
                .map(task -> (Task) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<SubTask> getSubTasks() {
        return store
                .getTasks()
                .stream()
                .filter(task -> task.getClass() == SubTask.class)
                .map(task -> (SubTask) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Epic> getEpics() {
        return store
                .getTasks()
                .stream()
                .filter(task -> task.getClass() == Epic.class)
                .map(task -> (Epic) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void removeTasks() {
        store.removeTasks();
    }

    public AbstractTask getTask(Integer id) {
        return store.getTask(id);
    }

    public void createTask(AbstractTask task) {
        task.add();
    }

    public void updateTask(AbstractTask task) {
        task.update();
    }

    public void removeTask(int taskId) {
        store.getTask(taskId).remove();
    }

    public ArrayList<SubTask> getEpicSubTasks(Epic epic) {
        return epic.getSubTasks();
    }

}
