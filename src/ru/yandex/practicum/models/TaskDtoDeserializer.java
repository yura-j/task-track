package ru.yandex.practicum.models;

import com.google.gson.*;
import ru.yandex.practicum.exceptions.JsonSerializingException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

public class TaskDtoDeserializer implements JsonDeserializer<TaskDto> {
    public static final List<String> NECESSARY_PROPERTY_LIST = List.of(
            "id",
            "type",
            "name",
            "status",
            "description",
            "epicId",
            "startTime",
            "duration"
    );

    @Override
    public TaskDto deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();
        TaskDto dto = new TaskDto();
        if (!checkAllFieldsPresented(json)) {
            throw new JsonSerializingException("Ошибка сериализации. Объект задачи содержит не все необходимые поля");
        }
        try {
            dto.id = json.get("id").getAsJsonPrimitive().getAsInt();
            dto.type = TaskType.valueOf(json.get("type").getAsJsonPrimitive().getAsString());
            dto.status = TaskStatus.valueOf(json.get("status").getAsJsonPrimitive().getAsString());
            dto.name = json.get("name").getAsJsonPrimitive().getAsString();
            dto.description = json.get("description").getAsJsonPrimitive().getAsString();

            dto.epicId = json.get("epicId").getAsJsonPrimitive().getAsInt();
            String stringValueOfTime = json.get("startTime").getAsJsonPrimitive().getAsString();
            if (!stringValueOfTime.isBlank()) {
                dto.startTime = LocalDateTime.parse(stringValueOfTime);
            }
            dto.duration = json.get("duration").getAsJsonPrimitive().getAsInt();

        } catch (Exception e) {
            throw new JsonSerializingException("Ошибка сериализации. Не получилось десериализовать объект");
        }


        return dto;
    }

    private boolean checkAllFieldsPresented(JsonObject jsonObject) {
        return NECESSARY_PROPERTY_LIST
                .stream()
                .map(jsonObject::has)
                .reduce(true, (multiplication, field) -> multiplication && field);
    }
}
