package ru.yandex.practicum.util;

import ru.yandex.practicum.managers.*;

import java.nio.file.Path;

final public class Managers {
    private static final String FILE_STORE = "./assets/state";
    private static InMemoryTaskStore inMemoryTaskStore = null;
    private final static InMemoryTaskManager inMemoryTaskHistory = null;
    private static InMemoryTaskHistoryManager inMemoryTaskHistoryManager = null;

    private static FileBackedTaskStore fileBackedTaskStore = null;
    private static FileBackedTaskManager fileBackedTaskManager = null;
    private static FileBackedTaskHistoryManager fileBackedTaskHistoryManager = null;


    public static TaskManager getDefaultTaskManager() {
        return getFileBackedTaskManager();
    }

    public static TaskStore getDefaultStore() {
        return getFileBackedTaskStore();
    }

    public static FileBackedTaskStore getFileBackedTaskStore() {
        if (fileBackedTaskStore == null) {
            fileBackedTaskStore = new FileBackedTaskStore();
        }
        return fileBackedTaskStore;
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
}
