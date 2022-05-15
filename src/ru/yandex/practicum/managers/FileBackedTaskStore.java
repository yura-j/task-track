package ru.yandex.practicum.managers;

import ru.yandex.practicum.exceptions.ParsingException;
import ru.yandex.practicum.models.*;
import ru.yandex.practicum.util.Compressible;
import ru.yandex.practicum.util.Observable;
import ru.yandex.practicum.util.Observer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileBackedTaskStore extends InMemoryTaskStore implements Compressible, Observable {

    List<Observer> observers = new ArrayList<>();

    @Override
    public String compress() {
        List<String> compressedDataParts =
                getTasks()
                        .stream()
                        .map(task -> {
                            CompressedTaskDto dto = task.toCompressedTaskDto();
                            return dto.compress();
                        })
                        .collect(Collectors.toList());
        return String.join("\n", compressedDataParts);
    }


    @Override
    public void decompress(String compressedData) {
        if (!compressedData.contains("\n")) {
            throw new ParsingException("Не могу прочитать строку заголовков CSV части");
        }
        String headers = compressedData.substring(0, compressedData.indexOf("\n"));
        Map<String, Integer> compressionMap = new HashMap<>();
        String[] fields = headers.split(",");
        for (int i = 0; i < fields.length; i++) {
            compressionMap.put(fields[i], i);
        }
        compressedData = compressedData.substring(compressedData.indexOf("\n") + 1);
        List<CompressedTaskDto> taskDtoList = Stream.of(compressedData.split("\n"))
                .map(stringDto -> {
                    CompressedTaskDto dto = new CompressedTaskDto();
                    dto.setCompressionMap(compressionMap);
                    dto.decompress(stringDto);
                    return dto;
                }).collect(Collectors.toList());
        int maxId = taskDtoList
                .stream()
                .mapToInt(dto -> dto.id)
                .max().orElse(0);
        Stream<CompressedTaskDto> epicDto = taskDtoList.stream().filter(dto -> dto.type == TaskType.EPIC);
        Stream<CompressedTaskDto> taskDto = taskDtoList.stream().filter(dto -> dto.type == TaskType.TASK);
        Stream<CompressedTaskDto> subTaskDto = taskDtoList.stream().filter(dto -> dto.type == TaskType.SUBTASK);

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
    public void addTask(AbstractTask task) {
        super.addTask(task);
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
}
