package ru.yandex.practicum.app;

import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.controllers.*;

public class Routes {
    HttpServer server;

    public Routes(HttpServer server) {
        this.server = server;
        Route.of(server, "/")
                .group("tasks/", tasksRoute -> {
                    tasksRoute.add("", new PrioritizedTasksController());
                    tasksRoute.add("history/", new HistoryController());
                    tasksRoute.add("task/", new TaskController());
                    tasksRoute.add("epic/", new EpicController());
                    tasksRoute.group("subtask/", tasksSubtaskRoute -> {
                        tasksSubtaskRoute.add("", new SubTaskController());
                        tasksSubtaskRoute.add("epic/", new EpicSubTasksController());
                    });
                })
                .add("silent/task/get/", new SilentTaskGetController())
                .terminate();
    }
}
