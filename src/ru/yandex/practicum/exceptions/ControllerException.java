package ru.yandex.practicum.exceptions;

public class ControllerException extends RuntimeException {
    public ControllerException(String message) {
        super(message);
    }
}
