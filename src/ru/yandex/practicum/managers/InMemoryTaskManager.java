package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.*;
import ru.yandex.practicum.util.Managers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

final public class InMemoryTaskManager implements TaskManager {

    private final InMemoryTaskStore store = Managers.getInMemoryStore();
    private final InMemoryTaskHistoryManager history = Managers.getInMemoryTaskHistory();


    public ArrayList<AbstractTask> getAllTasks() {
        return store.getTasks();
    }

    /**
     * Взять все задачи
     *
     * @return
     */
    public ArrayList<Task> getTasks() {
        return store
                .getTasks()
                .stream()
                .filter(task -> task.getClass() == Task.class)
                .map(task -> (Task) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Взять все подзадачи
     *
     * @return
     */
    public ArrayList<SubTask> getSubTasks() {
        return store
                .getTasks()
                .stream()
                .filter(task -> task.getClass() == SubTask.class)
                .map(task -> (SubTask) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Взять все подзадачи эпика
     *
     * @param epic
     * @return
     */
    public ArrayList<SubTask> getEpicSubTasks(Epic epic) {
        return epic.getSubTasks();
    }

    /**
     * Взять все эпики
     *
     * @return
     */
    public ArrayList<Epic> getEpics() {
        return store
                .getTasks()
                .stream()
                .filter(task -> task.getClass() == Epic.class)
                .map(task -> (Epic) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Удалить задачи
     */
    public void removeTasks() {
        store.removeTasks();
    }

    /**
     * Взять задачу по идентификатору
     *
     * @param id
     * @return
     */
    public AbstractTask getTask(Integer id) {
        AbstractTask observableTask = store.getTask(id);
        history.update(observableTask);
        return observableTask;
    }

    /**
     * Создать задачу
     *
     * @param task
     */
    public void createTask(AbstractTask task) {
        task.add();
    }

    /**
     * Обновить задачу
     *
     * @param task
     */
    public void updateTask(AbstractTask task) {
        task.update();
    }

    /**
     * Удалить задачу
     *
     * @param taskId
     */
    public void removeTask(int taskId) {
        store.getTask(taskId).remove();
    }

    public List<AbstractTask> history() {
        return history.getHistory();
    }

}
