package ru.yandex.practicum.managers;

import ru.yandex.practicum.app.HttpServerResponse;
import ru.yandex.practicum.models.*;
import ru.yandex.practicum.util.Json;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

import static ru.yandex.practicum.app.AbstractController.ResponseStatus.success;

public class RemoteClientTaskManager implements TaskManager {

    @Override
    public Set<AbstractTask> getPrioritizedTasks() {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/"))
                .GET()
                .build();
        HttpServerResponse result = send(request);
        ArrayList<Object> taskList = (ArrayList) result.payload;
        return taskList
                .stream()
                .map(this::morphObjectToTaskDto)
                .map(this::getTaskFromDto)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    //По заданию на этот метод нет ендпоинта, поэтому не будем его добавлять
    @Override
    public ArrayList<AbstractTask> getAllTasks() {
        return new ArrayList<>(this.getPrioritizedTasks());
    }

    @Override
    public ArrayList<Task> getTasks() {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task/"))
                .GET()
                .build();
        HttpServerResponse result = send(request);
        List<AbstractTask> answer = null;
        Collection<Object> taskList = (Collection) result.payload;
        return taskList
                .stream()
                .map(this::morphObjectToTaskDto)
                .map(this::getTaskFromDto)
                .map(task -> (Task) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask/"))
                .GET()
                .build();
        HttpServerResponse result = send(request);
        List<AbstractTask> answer = null;
        Collection<Object> taskList = (Collection) result.payload;
        return taskList
                .stream()
                .map(this::morphObjectToTaskDto)
                .map(this::getTaskFromDto)
                .map(task -> (SubTask) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<SubTask> getEpicSubTasks(Epic epic) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/subtask/epic/" + "?id=" + epic.getId()))
                .GET()
                .build();
        HttpServerResponse result = send(request);
        List<AbstractTask> answer = null;
        Collection<Object> taskList = (Collection) result.payload;
        return taskList
                .stream()
                .map(this::morphObjectToTaskDto)
                .map(this::getTaskFromDto)
                .map(task -> (SubTask) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public ArrayList<Epic> getEpics() {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/epic/"))
                .GET()
                .build();
        HttpServerResponse result = send(request);
        List<AbstractTask> answer = null;
        Collection<Object> taskList = (Collection) result.payload;
        return taskList
                .stream()
                .map(this::morphObjectToTaskDto)
                .map(this::getTaskFromDto)
                .map(task -> (Epic) task)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void removeTasks() {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/task/"))
                .DELETE()
                .build();
        HttpServerResponse result = send(request);
    }

    @Override
    public AbstractTask getTask(Integer id) {
        TaskDto dto = getTaskSilently(id);
        if (null == dto) {
            return null;
        }
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(getUriByTaskType(dto.type) + "?id=" + id))
                .GET()
                .build();
        HttpServerResponse result = send(request);
        dto = morphObjectToTaskDto(result.payload);
        return getTaskFromDto(dto);
    }

    @Override
    public void createTask(AbstractTask task) {
        HttpClient client = HttpClient.newHttpClient();
        String json = Json.build().to(task.toTaskDto());
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(getUriByTaskType(task.getType()))
                .POST(body)
                .build();
        HttpServerResponse result = send(request);
    }

    @Override
    public void updateTask(AbstractTask task) {
        String json = Json.build().to(task.toTaskDto());
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(getUriByTaskType(task.getType()))
                .POST(body)
                .build();
        HttpServerResponse result = send(request);
    }

    @Override
    public void removeTask(int taskId) {
        TaskDto dto = getTaskSilently(taskId);
        if (null == dto) {
            return;
        }
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(getUriByTaskType(dto.type) + "?id=" + taskId))
                .DELETE()
                .build();
        HttpServerResponse result = send(request);
    }

    @Override
    public List<AbstractTask> history() {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/tasks/history/"))
                .GET()
                .build();
        HttpServerResponse result = send(request);
        List<AbstractTask> answer = null;
        Collection<Object> taskList = (Collection) result.payload;
        return taskList
                .stream()
                .map(this::morphObjectToTaskDto)
                .map(this::getTaskFromDto)
                .collect(Collectors.toList());
    }

    private URI getUriByTaskType(TaskType type) {
        URI uri = null;
        switch (type) {
            case TASK:
                uri = URI.create("http://localhost:8080/tasks/task/");
                break;
            case EPIC:
                uri = URI.create("http://localhost:8080/tasks/epic/");
                break;
            case SUBTASK:
                uri = URI.create("http://localhost:8080/tasks/subtask/");
                break;
        }
        return uri;
    }

    public TaskDto getTaskSilently(int taskId) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create("http://localhost:8080/silent/task/get/?id=" + taskId))
                .GET()
                .build();
        HttpServerResponse result = send(request);
        TaskDto dto;
        if (result.status != success) {
            return null;
        }
        try {
            dto = morphObjectToTaskDto(result.payload);
        } catch (Exception e) {
            e.printStackTrace();
            dto = null;
        }
        return dto;
    }

    private HttpServerResponse send(HttpRequest request) {
        HttpServerResponse result = null;
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = Json.build().from(response.body(), HttpServerResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            System.out.println("sadasd");
        }
        return result;
    }

    private TaskDto morphObjectToTaskDto(Object any) {
        return Json.build().from(Json.build().to(any), TaskDto.class);
    }

    private AbstractTask getTaskFromDto(TaskDto dto) {
        AbstractTask task = null;
        switch (dto.type) {
            case SUBTASK:
                task = new SubTask(dto);
                break;
            case TASK:
                task = new Task(dto);
                break;
            case EPIC:
                task = new Epic(dto);
                break;
        }
        return task;
    }
}
