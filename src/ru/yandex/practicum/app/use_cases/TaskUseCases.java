package ru.yandex.practicum.app.use_cases;

import ru.yandex.practicum.exceptions.ControllerException;
import ru.yandex.practicum.managers.TaskManager;
import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.models.Task;
import ru.yandex.practicum.models.TaskDto;
import ru.yandex.practicum.models.TaskType;
import ru.yandex.practicum.util.Managers;

import java.util.List;
import java.util.stream.Collectors;

abstract public class TaskUseCases {
    private static final TaskManager manager = Managers.getDefaultTaskManager();

    public static TaskDto getOneTask(int id) {
        Task task = (Task) manager.getTask(id);
        if (null == task) {
            throw new ControllerException("Задача не найдена");
        }
        return task.toTaskDto();
    }

    public static List<TaskDto> getAllTasks() {
        return  manager
                .getTasks()
                .stream()
                .map(AbstractTask::toTaskDto)
                .collect(Collectors.toList());
    }

    public static TaskDto putTask(TaskDto dto) {
        if (dto.type != TaskType.TASK) {
            throw new ControllerException("Ошибка. Тип задачи не соответствует");
        }
        Task task = new Task(dto);
        if (task.getId() == 0) {
            manager.createTask(task);
        } else {
            manager.updateTask(task);
        }

        return task.toTaskDto();
    }

    public static void removeTask(int id) {
        manager.removeTask(id);
    }

    public static void removeTasks() {
        manager.removeTasks();
    }

}
