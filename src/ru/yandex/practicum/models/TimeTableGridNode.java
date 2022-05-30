package ru.yandex.practicum.models;

public class TimeTableGridNode {
    private boolean isBusy = false;
    private int tasksCount = 0;

    public boolean canHold() {
        return !isBusy;
    }

    public boolean hold() {
        isBusy = true;
        tasksCount++;
        return true;
    }

    public boolean free() {
        isBusy = false;
        tasksCount = 0;
        return true;
    }
}
