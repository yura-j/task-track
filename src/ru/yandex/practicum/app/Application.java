package ru.yandex.practicum.app;

public class Application {
    HttpTaskServer taskServer;

    public void start() {
        try {
            taskServer = new HttpTaskServer();
            taskServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stop() {
        taskServer.stop();
    }
}
