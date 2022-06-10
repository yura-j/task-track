package ru.yandex.practicum.controllers;

import ru.yandex.practicum.app.AbstractController;
import ru.yandex.practicum.app.use_cases.SubTaskUseCases;
import ru.yandex.practicum.models.TaskDto;
import ru.yandex.practicum.util.Json;

final public class SubTaskController extends AbstractController {
    @Override
    protected void get() {
        if (queryArguments.containsKey("id")) {
            int id = Integer.parseInt(queryArguments.get("id"));
            response.payload = SubTaskUseCases.getOneTask(id);
        } else {
            response.payload = SubTaskUseCases.getAllTasks();
        }
    }

    @Override
    protected void post() {
        String jsonTask = getBody();
        TaskDto dto = Json.build().from(jsonTask, TaskDto.class);
        response.payload = SubTaskUseCases.putTask(dto);
    }

    @Override
    protected void delete() {
        if (queryArguments.containsKey("id")) {
            int id = Integer.parseInt(queryArguments.get("id"));
            SubTaskUseCases.removeTask(id);
        } else {
            SubTaskUseCases.removeTasks();
        }
    }

}
