package ru.yandex.practicum.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.models.*;
import ru.yandex.practicum.util.Managers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest {

    TaskManager manager;
    TaskDto sample;

    @BeforeEach
    void init() {

        sample = new TaskDto();
        sample.id = 0;
        sample.name = "";
        sample.description = "";
        sample.status = TaskStatus.NEW;
        sample.startTime = LocalDateTime
                .of(LocalDate.now(), LocalTime.MIN)
                .plusDays(1); //Сегодня не все могут в завтрашний день смотреть
        sample.duration = 15;
    }

    @AfterEach
    void resetTasks() {
        manager.removeTasks();
    }

    @Test
    void getPrioritizedTasks() {
        sample.status=TaskStatus.IN_PROGRESS;
        Task taskOld = new Task(sample);

        sample.status=TaskStatus.NEW;
        sample.startTime = sample.startTime.plusMinutes(15);
        Task taskYoung = new Task(sample);

        manager.createTask(taskOld);
        manager.createTask(taskYoung);
        taskOld = (Task) manager.getTask(1);
        taskYoung = (Task) manager.getTask(2);

        Set<AbstractTask> tasks = manager.getPrioritizedTasks();
        Iterator<AbstractTask> iterator = tasks.iterator();
        Task actualTask = (Task) iterator.next();
        assertEquals(taskYoung, actualTask);
        actualTask = (Task) iterator.next();
        assertEquals(taskOld, actualTask);
    }
    @Test
    void getPrioritizedTasksWithNullStartTime() {
        Task taskOld = new Task(sample);
        sample.startTime = null;
        Task taskNull = new Task(sample);

        manager.createTask(taskOld);
        manager.createTask(taskNull);

        taskOld = (Task) manager.getTask(1);
        taskNull = (Task) manager.getTask(2);

        Set<AbstractTask> tasks = manager.getPrioritizedTasks();
        Iterator<AbstractTask> iterator = tasks.iterator();
        Task actualTask = (Task) iterator.next();
        assertEquals(taskOld, actualTask);
        actualTask = (Task) iterator.next();
        assertEquals(taskNull, actualTask);

    }

    @Test
    void getAllTasks1In1Out() {
        Task task = new Task(sample);
        manager.createTask(task);
        ArrayList<AbstractTask> tasks = manager.getAllTasks();
        assertEquals( 1, tasks.size());
    }

    @Test
    void getAllTasks0In0Out() {
        ArrayList<AbstractTask> tasks = manager.getAllTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    void getTasks1Task1SubTask() {
        Task task = new Task(sample);
        manager.createTask(task);

        SubTask subTask = new SubTask(sample);
        manager.createTask(subTask);

        ArrayList<Task> tasks = manager.getTasks();
        assertEquals(1, tasks.size());
    }

    @Test
    void getTasks0Task1SubTask() {
        SubTask subTask = new SubTask(sample);
        manager.createTask(subTask);
        ArrayList<Task> tasks = manager.getTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    void getSubTasks1Task1SubTask() {
        Task task = new Task(sample);
        manager.createTask(task);

        SubTask subTask = new SubTask(sample);
        manager.createTask(subTask);

        ArrayList<SubTask> tasks = manager.getSubTasks();
        assertEquals(1, tasks.size());
    }

    @Test
    void getSubTasks1Task0SubTask() {
        Task task = new Task(sample);
        manager.createTask(task);
        ArrayList<SubTask> tasks = manager.getSubTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    void getEpics1Task0Epic() {
        Task task = new Task(sample);
        manager.createTask(task);

        ArrayList<Epic> tasks = manager.getEpics();
        assertEquals(0, tasks.size());
    }

    @Test
    void getEpics1Task1Epic() {
        Task task = new Task(sample);
        manager.createTask(task);

        Epic epic = new Epic(sample);
        manager.createTask(epic);

        ArrayList<Epic> tasks = manager.getEpics();
        assertEquals(1, tasks.size());
    }

    @Test
    void getEpicSubTasks0subTasks() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);

        assertEquals(0, manager.getEpicSubTasks(epic).size());
    }

    @Test
    void getEpicSubTasks1subTask() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);
        epic = (Epic) manager.getTask(1);

        SubTask subTask = new SubTask(sample);
        subTask.setEpic(epic);
        manager.createTask(subTask);

        assertEquals(1, manager.getEpicSubTasks(epic).size());

    }

    @Test
    void removeTasksEmptyList() {
        manager.removeTasks();
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    void removeTasks1Task() {
        SubTask subTask = new SubTask(sample);
        manager.createTask(subTask);
        manager.removeTasks();
        assertEquals(0, manager.getAllTasks().size());
    }

    @Test
    void getTaskIn0OutNull() {
        assertNull(manager.getTask(0));
    }

    @Test
    void getTaskIn1OutTask() {
        Task task = new Task(sample);
        manager.createTask(task);
        sample.id = 1;
        task = new Task(sample);
        assertEquals(task, manager.getTask(1));
    }

    @Test
    void createTask1Task() {
        int sizeBeforeCreation = manager.getAllTasks().size();
        Task task = new Task(sample);
        manager.createTask(task);
        assertEquals(1, manager.getAllTasks().size() - sizeBeforeCreation);
    }

    @Test
    void removeTask1Task() {
        Task task = new Task(sample);
        manager.createTask(task);
        int sizeBeforeDeletion = manager.getAllTasks().size();
        manager.removeTask(1);
        assertEquals(1, sizeBeforeDeletion - manager.getAllTasks().size());
    }

    @Test
    void removeTask0Task() {
        int sizeBeforeDeletion = manager.getAllTasks().size();
        manager.removeTask(0);
        assertEquals(0, sizeBeforeDeletion - manager.getAllTasks().size());
    }

    @Test
    void updateTaskFieldChanged() {
        Task task = new Task(sample);
        manager.createTask(task);
        sample.id = 1;
        sample.description = "new description";
        Task task2 = new Task(sample);
        manager.updateTask(task2);
        AbstractTask taskFromStore = manager.getTask(1);
        assertEquals("new description", taskFromStore.getDescription());
    }

    @Test
    void updateTaskFieldNotChanged() {
        Task task = new Task(sample);
        manager.createTask(task);
        sample.id = 1;
        Task task2 = new Task(sample);
        manager.updateTask(task2);
        AbstractTask taskFromStore = manager.getTask(1);
        assertEquals(task.getDescription(), taskFromStore.getDescription());
    }

    @Test
    void EpicStatusEmpty() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);
        epic = (Epic) manager.getTask(1);
        assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    void EpicStatusWithOnlyNewSubTasks() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);
        epic = (Epic) manager.getTask(1);

        SubTask subTask = new SubTask(sample);
        subTask.setEpic(epic);
        manager.createTask(subTask);

        SubTask subTask2 = new SubTask(sample);
        subTask2.setEpic(epic);
        manager.createTask(subTask2);

        Epic taskFromStore = (Epic) manager.getTask(1);
        assertEquals(TaskStatus.NEW, taskFromStore.getStatus());
    }

    @Test
    void EpicStatusWithOnlyDoneSubTasks() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);
        epic = (Epic) manager.getTask(1);

        sample.status = TaskStatus.DONE;
        SubTask subTask = new SubTask(sample);
        subTask.setEpic(epic);
        manager.createTask(subTask);

        SubTask subTask2 = new SubTask(sample);
        subTask2.setEpic(epic);
        manager.createTask(subTask2);
        Epic taskFromStore = (Epic) manager.getTask(1);
        assertEquals(TaskStatus.DONE, taskFromStore.getStatus());
    }

    @Test
    void EpicStatusWithNewAndDoneSubTasks() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);
        epic = (Epic) manager.getTask(1);

        SubTask subTask = new SubTask(sample);
        subTask.setEpic(epic);
        manager.createTask(subTask);

        sample.status = TaskStatus.DONE;
        SubTask subTask2 = new SubTask(sample);
        subTask2.setEpic(epic);
        manager.createTask(subTask2);
        Epic taskFromStore = (Epic) manager.getTask(1);
        assertEquals(TaskStatus.IN_PROGRESS, taskFromStore.getStatus());
    }

    @Test
    void EpicStatusWithOnlyInProgressSubTasks() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);
        epic = (Epic) manager.getTask(1);


        sample.status = TaskStatus.IN_PROGRESS;
        SubTask subTask = new SubTask(sample);

        subTask.setEpic(epic);
        manager.createTask(subTask);


        SubTask subTask2 = new SubTask(sample);
        subTask2.setEpic(epic);
        manager.createTask(subTask2);

        Epic taskFromStore = (Epic) manager.getTask(1);
        assertEquals(TaskStatus.IN_PROGRESS, taskFromStore.getStatus());
    }

    @Test
    void EpicStatusAllDifferentSubTasks() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);
        epic = (Epic) manager.getTask(1);

        SubTask subTask3 = new SubTask(sample);
        subTask3.setEpic(epic);
        manager.createTask(subTask3);

        sample.status = TaskStatus.IN_PROGRESS;
        SubTask subTask = new SubTask(sample);
        subTask.setEpic(epic);
        manager.createTask(subTask);

        sample.status = TaskStatus.DONE;
        SubTask subTask2 = new SubTask(sample);
        subTask2.setEpic(epic);
        manager.createTask(subTask2);

        Epic taskFromStore = (Epic) manager.getTask(1);
        assertEquals(TaskStatus.IN_PROGRESS, taskFromStore.getStatus());
    }

    @Test
    void EpicStatusNewAndInProgressSubTasks() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);
        epic = (Epic) manager.getTask(1);

        SubTask subTask3 = new SubTask(sample);
        subTask3.setEpic(epic);
        manager.createTask(subTask3);

        sample.status = TaskStatus.IN_PROGRESS;
        SubTask subTask = new SubTask(sample);
        subTask.setEpic(epic);
        manager.createTask(subTask);

        Epic taskFromStore = (Epic) manager.getTask(1);
        assertEquals(TaskStatus.IN_PROGRESS, taskFromStore.getStatus());
    }

    @Test
    void EpicStatusDoneAndInProgressSubTasks() {
        Epic epic = new Epic(sample);
        manager.createTask(epic);
        epic = (Epic) manager.getTask(1);

        sample.status = TaskStatus.IN_PROGRESS;
        SubTask subTask = new SubTask(sample);
        subTask.setEpic(epic);
        manager.createTask(subTask);

        sample.status = TaskStatus.DONE;
        SubTask subTask2 = new SubTask(sample);
        subTask2.setEpic(epic);
        manager.createTask(subTask2);

        Epic taskFromStore = (Epic) manager.getTask(1);
        assertEquals(TaskStatus.IN_PROGRESS, taskFromStore.getStatus());
    }

    @Test
    void historyAdd0Return0() {
        assertEquals(0, manager.history().size());
    }

    @Test
    void historyAdd1Return1() {
        Task task = new Task(sample);
        manager.createTask(task);
        manager.getTask(1);
        assertEquals(1, manager.history().size());
    }

    @Test
    void historyAdd2DifferentReturn2() {
        Task task = new Task(sample);
        manager.createTask(task);

        Task task2 = new Task(sample);
        manager.createTask(task2);

        manager.getTask(1);
        manager.getTask(2);

        assertEquals(2, manager.history().size());
    }

    @Test
    void historyAdd2SameReturn1() {
        Task task = new Task(sample);
        manager.createTask(task);

        Task task2 = new Task(sample);
        manager.createTask(task2);

        manager.getTask(1);
        manager.getTask(1);

        int actual = manager.history().size();
        assertEquals(1, actual);
    }

    @Test
    void historyAdd2DifferentRemove1Return1() {
        Task task = new Task(sample);
        manager.createTask(task);

        Task task2 = new Task(sample);
        manager.createTask(task2);

        manager.getTask(1);
        manager.getTask(2);

        manager.removeTask(2);
        assertEquals(1, manager.history().size());
    }


}