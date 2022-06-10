package ru.yandex.practicum.models;

import com.google.gson.*;

import java.lang.reflect.Type;

public class TaskDtoSerializer implements JsonSerializer<TaskDto> {

    @Override
    public JsonElement serialize(TaskDto dto, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject serializedTask = new JsonObject();
        serializedTask.add("id", new JsonPrimitive(dto.id));
        serializedTask.add("type", new JsonPrimitive(String.valueOf(dto.type)));
        serializedTask.add("name", new JsonPrimitive(dto.name));
        serializedTask.add("status", new JsonPrimitive(String.valueOf(dto.status)));
        serializedTask.add("description", new JsonPrimitive(dto.description));
        serializedTask.add("epicId", new JsonPrimitive(dto.epicId));
        serializedTask.add("startTime", new JsonPrimitive(dto.exportStartTimeToString()));
        serializedTask.add("duration", new JsonPrimitive(dto.duration));
        return serializedTask;
    }
}
