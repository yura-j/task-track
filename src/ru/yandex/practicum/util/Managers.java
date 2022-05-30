package ru.yandex.practicum.util;

import ru.yandex.practicum.managers.*;
import ru.yandex.practicum.managers.TimeTable;

import java.nio.file.Path;

final public class Managers {
    private static final String FILE_STORE = "./assets/state";
    private static ApplicationMode MODE;
    private static InMemoryTaskStore inMemoryTaskStore = null;
    private static InMemoryTaskManager inMemoryTaskManager = null;
    private static InMemoryTaskHistoryManager inMemoryTaskHistoryManager = null;

    private static FileBackedTaskStore fileBackedTaskStore = null;
    private static FileBackedTaskManager fileBackedTaskManager = null;
    private static FileBackedTaskHistoryManager fileBackedTaskHistoryManager = null;

    private static TimeTable timeTable = null;

    public static void setApplicationMode(ApplicationMode mode) {
        MODE = mode;
    }

    public static TaskManager getDefaultTaskManager() {
        switch (MODE) {
            case FILE_BACKING:
                return getFileBackedTaskManager();
            default:
                return getInMemoryTaskManager();
        }
    }

    public static TaskStore getDefaultStore() {
        switch (MODE) {
            case FILE_BACKING:
                return getFileBackedTaskStore();
            default :
                return getInMemoryStore();
        }
    }

    public static FileBackedTaskStore getFileBackedTaskStore() {
        if (fileBackedTaskStore == null) {
            fileBackedTaskStore = new FileBackedTaskStore();
        }
        return fileBackedTaskStore;
    }

    public static TimeTable getTimeTable() {
        if (timeTable == null) {
            timeTable = new TimeTable();
        }
        return timeTable;
    }

    public static void resetTimeTable() {
        timeTable = new TimeTable();
    }

    public static FileBackedTaskManager getFileBackedTaskManager() {
        if (fileBackedTaskManager == null) {
            fileBackedTaskManager = FileBackedTaskManager.loadFromFile(Path.of(FILE_STORE));
        }
        return fileBackedTaskManager;
    }

    public static FileBackedTaskHistoryManager getFileBackedTaskHistoryManager() {
        if (fileBackedTaskHistoryManager == null) {
            fileBackedTaskHistoryManager = new FileBackedTaskHistoryManager();
        }
        return fileBackedTaskHistoryManager;
    }


    public static InMemoryTaskStore getInMemoryStore() {
        if (inMemoryTaskStore == null) {
            inMemoryTaskStore = new InMemoryTaskStore();
        }
        return inMemoryTaskStore;
    }

    public static InMemoryTaskHistoryManager getInMemoryTaskHistory() {
        if (inMemoryTaskHistoryManager == null) {
            inMemoryTaskHistoryManager = new InMemoryTaskHistoryManager();
        }
        return inMemoryTaskHistoryManager;
    }

    public static InMemoryTaskManager getInMemoryTaskManager() {
        if (inMemoryTaskManager == null) {
            inMemoryTaskManager = new InMemoryTaskManager();

        }
        return inMemoryTaskManager;
    }
}
