package ru.yandex.practicum.controllers;

import ru.yandex.practicum.app.AbstractController;
import ru.yandex.practicum.exceptions.ControllerException;
import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.util.Managers;

final public class SilentTaskGetController extends AbstractController {
    @Override
    protected void get() {
        if (queryArguments.containsKey("id")) {
            int id = Integer.parseInt(queryArguments.get("id"));
            AbstractTask task = Managers.getDefaultStore().getTask(id);
            if (null == task) {
                throw new ControllerException("Задача не найдена");
            }
            response.payload = task.toTaskDto();
        }
    }

    @Override
    protected void post() {
    }

    @Override
    protected void delete() {
    }

}
