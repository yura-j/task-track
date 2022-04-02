package ru.yandex.practicum.managers;

import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.models.Epic;
import ru.yandex.practicum.models.SubTask;
import ru.yandex.practicum.models.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    /**
     * Взять все задачи,епики и подзадачи
     *
     * @return
     */
    ArrayList<AbstractTask> getAllTasks();

    /**
     * Взять все задачи
     *
     * @return
     */
    ArrayList<Task> getTasks();

    /**
     * Взять все подзадачи
     *
     * @return
     */
    ArrayList<SubTask> getSubTasks();

    /**
     * Взять все подзадачи эпика
     *
     * @param epic
     * @return
     */
    ArrayList<SubTask> getEpicSubTasks(Epic epic);

    /**
     * Взять все эпики
     *
     * @return
     */
    ArrayList<Epic> getEpics();

    /**
     * Удалить задачи
     */
    void removeTasks();

    /**
     * Взять задачу по идентификатору
     *
     * @param id
     * @return
     */
    AbstractTask getTask(Integer id);

    /**
     * Создать задачу
     *
     * @param task
     */
    void createTask(AbstractTask task);

    /**
     * Обновить задачу
     *
     * @param task
     */
    void updateTask(AbstractTask task);

    /**
     * Удалить задачу
     *
     * @param taskId
     */
    void removeTask(int taskId);

    List<AbstractTask> history();
}
