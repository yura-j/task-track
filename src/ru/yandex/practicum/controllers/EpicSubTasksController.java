package ru.yandex.practicum.controllers;

import ru.yandex.practicum.app.AbstractController;
import ru.yandex.practicum.exceptions.ControllerException;
import ru.yandex.practicum.managers.TaskManager;
import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.models.Epic;
import ru.yandex.practicum.models.TaskType;
import ru.yandex.practicum.util.Managers;

import java.util.stream.Collectors;

final public class EpicSubTasksController extends AbstractController {
    @Override
    protected void get() {
        TaskManager manager = Managers.getDefaultTaskManager();
        if (!queryArguments.containsKey("id")) {
            throw new ControllerException("id не задан");
        }
        int id = Integer.parseInt(queryArguments.get("id"));

        AbstractTask task = Managers.getDefaultStore().getTask(id);

        if (task.getType() != TaskType.EPIC) {
            throw new ControllerException("Задача не эпик");
        }
        Epic epic = (Epic) task;
        response.payload = manager
                .getEpicSubTasks(epic)
                .stream()
                .map(AbstractTask::toTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    protected void post() {
    }

    @Override
    protected void delete() {
    }

}
