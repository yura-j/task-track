package ru.yandex.practicum.managers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.app.Application;
import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.models.Task;
import ru.yandex.practicum.models.TaskDto;
import ru.yandex.practicum.models.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

class RemoteClientTaskManagerTest extends TaskManagerTest {

    private static Application app;

    @BeforeAll
    static void startServer() {
        app = new Application();
        app.start();

    }

    @AfterAll
    static void stopServer() {
        app.stop();
    }

    @BeforeEach
    @Override
    void init() {
        manager = new RemoteClientTaskManager();
        sample = new TaskDto();
        sample.id = 0;
        sample.name = "";
        sample.description = "";
        sample.status = TaskStatus.NEW;
        sample.startTime = LocalDateTime
                .of(LocalDate.now(), LocalTime.MIN)
                .plusDays(1);
        sample.duration = 15;
    }


    /*@Test
    void createTaskTest(){
        Task task = new Task(sample);
        manager.createTask(task);
    }

    @Test
    void historyTest(){
        Task task = new Task(sample);
        manager.createTask(task);
        manager.createTask(task);
        manager.createTask(task);
        manager.getTask(3);
        manager.getTask(1);
        List<AbstractTask> h = manager.history();
        System.out.println(h);
    }removeTasks
    /*getPrioritizedTasks
            getAllTasks
    getTasks
            getSubTasks
    getEpicSubTasks
            getEpics
    removeTasks
            getTask
    createTask
            updateTask
    removeTask
            history*/
}