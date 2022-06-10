package ru.yandex.practicum.managers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.kv_server.KVServer;
import ru.yandex.practicum.managers.http.HttpTaskManager;
import ru.yandex.practicum.util.Managers;

class HttpTaskManagerTest extends TaskManagerTest{

    private static KVServer kvServer;

    @BeforeAll
    static void startServer() {
        kvServer = new KVServer();
        kvServer.start();

    }

    @AfterAll
    static void stopServer() {
        kvServer.stop();
    }

    @BeforeEach
    @Override
    void init() {
        Managers.setApplicationMode(ApplicationMode.HTTP);
        manager = (HttpTaskManager) Managers.getDefaultTaskManager();
        super.init();
    }
}