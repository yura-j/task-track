package ru.yandex.practicum.exceptions;

public class JsonSerializingException extends RuntimeException {
    public JsonSerializingException(String message) {
        super(message);
    }
}
