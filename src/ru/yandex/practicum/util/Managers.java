package ru.yandex.practicum.util;

import ru.yandex.practicum.managers.*;
import ru.yandex.practicum.managers.TimeTable;
import ru.yandex.practicum.managers.file_backed.FileBackedTaskHistoryManager;
import ru.yandex.practicum.managers.file_backed.FileBackedTaskManager;
import ru.yandex.practicum.managers.file_backed.FileBackedTaskStore;
import ru.yandex.practicum.managers.http.HttpTaskHistoryManager;
import ru.yandex.practicum.managers.http.HttpTaskManager;
import ru.yandex.practicum.managers.http.HttpTaskStore;
import ru.yandex.practicum.managers.in_memory.InMemoryTaskHistoryManager;
import ru.yandex.practicum.managers.in_memory.InMemoryTaskManager;
import ru.yandex.practicum.managers.in_memory.InMemoryTaskStore;

import java.nio.file.Path;

final public class Managers {
    private static final String FILE_STORE = "./assets/state";
    private static final String KV_HOST = "http://localhost:8077";
    private static ApplicationMode MODE = ApplicationMode.HTTP;

    private static InMemoryTaskStore inMemoryTaskStore = null;
    private static InMemoryTaskManager inMemoryTaskManager = null;
    private static InMemoryTaskHistoryManager inMemoryTaskHistoryManager = null;

    private static FileBackedTaskStore fileBackedTaskStore = null;
    private static FileBackedTaskManager fileBackedTaskManager = null;
    private static FileBackedTaskHistoryManager fileBackedTaskHistoryManager = null;

    private static HttpTaskStore httpTaskStore = null;
    private static HttpTaskManager httpTaskManager = null;
    private static HttpTaskHistoryManager httpTaskHistoryManager = null;

    private static TimeTable timeTable = null;

    public static void setApplicationMode(ApplicationMode mode) {
        MODE = mode;
    }

    public static TaskManager getDefaultTaskManager() {
        switch (MODE) {
            case HTTP:
                return getHttpTaskManager();
            case FILE_BACKING:
                return getFileBackedTaskManager();
            default:
                return getInMemoryTaskManager();
        }
    }

    public static TaskStore getDefaultStore() {
        switch (MODE) {
            case HTTP:
                return getHttpTaskStore();
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

    public static HttpTaskStore getHttpTaskStore() {
        if (httpTaskStore == null) {
            httpTaskStore = new HttpTaskStore();
        }
        return httpTaskStore;
    }

    public static HttpTaskHistoryManager getHttpTaskHistoryManager() {
        if (httpTaskHistoryManager == null) {
            httpTaskHistoryManager = new HttpTaskHistoryManager();
        }
        return httpTaskHistoryManager;
    }

    public static HttpTaskManager getHttpTaskManager() {
        if (httpTaskManager == null) {
            httpTaskManager = HttpTaskManager.loadFromKVServer(KV_HOST);

        }
        return httpTaskManager;
    }
}
