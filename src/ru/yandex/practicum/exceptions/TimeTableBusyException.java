package ru.yandex.practicum.exceptions;

public class TimeTableBusyException extends RuntimeException {
    public TimeTableBusyException(String message) {
        super(message);
    }
}
