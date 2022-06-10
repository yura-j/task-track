package ru.yandex.practicum.controllers;

import ru.yandex.practicum.app.AbstractController;
import ru.yandex.practicum.app.use_cases.TaskUseCases;
import ru.yandex.practicum.models.TaskDto;
import ru.yandex.practicum.util.Json;

final public class TaskController extends AbstractController {
    @Override
    protected void get() {
        if (queryArguments.containsKey("id")) {
            int id = Integer.parseInt(queryArguments.get("id"));
            response.payload = TaskUseCases.getOneTask(id);
        } else {
            response.payload = TaskUseCases.getAllTasks();
        }
    }

    @Override
    protected void post() {
        String jsonTask = getBody();
        TaskDto dto = Json.build().from(jsonTask, TaskDto.class);
        response.payload = TaskUseCases.putTask(dto);
    }

    @Override
    protected void delete() {
        if (queryArguments.containsKey("id")) {
            int id = Integer.parseInt(queryArguments.get("id"));
            TaskUseCases.removeTask(id);
        } else {
            TaskUseCases.removeTasks();
        }
    }

}
