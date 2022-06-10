package ru.yandex.practicum.controllers;

import ru.yandex.practicum.app.AbstractController;
import ru.yandex.practicum.app.use_cases.EpicUseCases;
import ru.yandex.practicum.models.TaskDto;
import ru.yandex.practicum.util.Json;

final public class EpicController extends AbstractController {
    @Override
    protected void get() {
        if (queryArguments.containsKey("id")) {
            int id = Integer.parseInt(queryArguments.get("id"));
            response.payload = EpicUseCases.getOneTask(id);
        } else {
            response.payload = EpicUseCases.getAllTasks();
        }
    }

    @Override
    protected void post() {
        String jsonTask = getBody();
        TaskDto dto = Json.build().from(jsonTask, TaskDto.class);
        response.payload = EpicUseCases.putTask(dto);
    }

    @Override
    protected void delete() {
        if (queryArguments.containsKey("id")) {
            int id = Integer.parseInt(queryArguments.get("id"));
            EpicUseCases.removeTask(id);
        } else {
            EpicUseCases.removeTasks();
        }
    }

}
