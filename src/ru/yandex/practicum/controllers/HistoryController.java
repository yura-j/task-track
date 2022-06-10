package ru.yandex.practicum.controllers;

import ru.yandex.practicum.app.AbstractController;
import ru.yandex.practicum.managers.TaskManager;
import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.util.Managers;

import java.util.stream.Collectors;

final public class HistoryController extends AbstractController {
    @Override
    protected void get() {
        TaskManager manager = Managers.getDefaultTaskManager();
        response.payload = manager
                .history()
                .stream()
                .map(AbstractTask::toTaskDto)
                .collect(Collectors.toSet());
    }

    @Override
    protected void post() {
    }

    @Override
    protected void delete() {
    }

}
