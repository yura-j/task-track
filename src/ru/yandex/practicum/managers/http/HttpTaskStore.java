package ru.yandex.practicum.managers.http;

import ru.yandex.practicum.managers.in_memory.InMemoryTaskStore;
import ru.yandex.practicum.models.*;
import ru.yandex.practicum.util.Json;
import ru.yandex.practicum.util.Observable;
import ru.yandex.practicum.util.Observer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpTaskStore extends InMemoryTaskStore implements Observable {

    List<Observer> observers = new ArrayList<>();

    public String toJson() {
        List<TaskDto> listOfDTOs =
                getTasks()
                        .stream()
                        .map(AbstractTask::toTaskDto)
                        .collect(Collectors.toList());
        return Json.build().to(listOfDTOs);
    }

    public void fromJson(String json) {
        if (json.isBlank()) {
            this.maxId = 0;
            return;
        }
        List<TaskDto> dtoList = Arrays.asList(Json.build().from(json, TaskDto[].class));
        int maxId = dtoList
                .stream()
                .mapToInt(dto -> dto.id)
                .max().orElse(0);
        Stream<TaskDto> epicDto = dtoList.stream().filter(dto -> dto.type == TaskType.EPIC);
        Stream<TaskDto> taskDto = dtoList.stream().filter(dto -> dto.type == TaskType.TASK);
        Stream<TaskDto> subTaskDto = dtoList.stream().filter(dto -> dto.type == TaskType.SUBTASK);

        epicDto.forEach(dto -> {
            Epic epic = new Epic(dto);
            taskList.put(epic.getId(), epic);
        });

        taskDto.forEach(dto -> {
            Task task = new Task(dto);
            taskList.put(task.getId(), task);
        });

        subTaskDto.forEach(dto -> {
            SubTask subTask = new SubTask(dto);
            subTask.setEpic((Epic) getTask(dto.epicId));
            subTask.getEpic().addSubtask(subTask);
            taskList.put(subTask.getId(), subTask);
        });
        this.maxId = maxId;
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    @Override
    public void putTask(AbstractTask task) {
        super.putTask(task);
        notifyObservers();
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        notifyObservers();
    }

    @Override
    public void removeTasks() {
        super.removeTasks();
        notifyObservers();
    }

    @Override
    public AbstractTask getTask(Integer $id) {
        AbstractTask task = super.getTask($id);
        notifyObservers();
        return task;
    }
}
