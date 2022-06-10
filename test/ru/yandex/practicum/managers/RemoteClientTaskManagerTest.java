package ru.yandex.practicum.managers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.app.Application;
import ru.yandex.practicum.kv_server.KVServer;
import ru.yandex.practicum.models.TaskDto;
import ru.yandex.practicum.models.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class RemoteClientTaskManagerTest extends TaskManagerTest {

    private static Application app;
    private static KVServer kvServer;

    @BeforeAll
    static void startServer() {
        kvServer = new KVServer();
        kvServer.start();

        app = new Application();
        app.start();
    }

    @AfterAll
    static void stopServer() {
        app.stop();
        kvServer.stop();
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
}