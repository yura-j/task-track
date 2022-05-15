package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.*;
import ru.yandex.practicum.util.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTaskManager implements TaskManager {

    protected TaskStore store = Managers.getInMemoryStore();
    protected TaskHistoryManager history = Managers.getInMemoryTaskHistory();

    @Override
    public ArrayList<AbstractTask> getAllTasks() {
        return store.getTasks();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return store
                .getTasks()
                .stream()
                .filter(task -> task.getClass() == Task.class)
                .map(task -> (Task) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return store
                .getTasks()
                .stream()
                .filter(task -> task.getClass() == SubTask.class)
                .map(task -> (SubTask) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<SubTask> getEpicSubTasks(Epic epic) {
        return epic.getSubTasks();
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return store
                .getTasks()
                .stream()
                .filter(task -> task.getClass() == Epic.class)
                .map(task -> (Epic) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void removeTasks() {
        store.removeTasks();
    }

    @Override
    public AbstractTask getTask(Integer id) {
        AbstractTask observableTask = store.getTask(id);
        history.add(observableTask);
        return observableTask;
    }

    @Override
    public void createTask(AbstractTask task) {
        task.add();
    }

    @Override
    public void updateTask(AbstractTask task) {
        task.update();
    }

    @Override
    public void removeTask(int taskId) {
        history.remove(taskId);
        List<AbstractTask> linkedTasks = store.getTask(taskId).remove();
        linkedTasks
                .stream()
                .map(AbstractTask::getId)
                .forEach(this::removeTask);
    }

    @Override
    public List<AbstractTask> history() {
        return history.getHistory();
    }

}
