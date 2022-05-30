package ru.yandex.practicum.managers;

import ru.yandex.practicum.exceptions.ManagerLoadException;
import ru.yandex.practicum.exceptions.ManagerSaveException;
import ru.yandex.practicum.util.Managers;
import ru.yandex.practicum.util.Observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements Observer {
    Path file;
    static final String TASK_HEADING_LINE = "id,type,name,status,description,epic,start_time,duration";

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toFile()))) {
            writer.write(TASK_HEADING_LINE);
            writer.write("\n");
            writer.write(this.getStore().compress());
            writer.write("\n\n");
            writer.write(this.getHistory().compress());
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения");
        }
    }

    public FileBackedTaskStore getStore() {
        return (FileBackedTaskStore) this.store;
    }

    public FileBackedTaskHistoryManager getHistory() {
        return (FileBackedTaskHistoryManager) this.history;
    }

    FileBackedTaskManager(Path file, FileBackedTaskStore store, FileBackedTaskHistoryManager history) {
        this.file = file;
        this.store = store;
        this.history = history;
    }

    public static FileBackedTaskManager loadFromFile(Path file) throws ManagerLoadException {
        List<String> lines;
        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка загрузки файла");
        }
        StringBuilder taskDataBuilder = new StringBuilder("");
        String historyData = "";
        Iterator<String> linesIterator = lines.iterator();
        while (linesIterator.hasNext()) {
            String line = linesIterator.next();
            if (line.isEmpty()) {
                break;
            }
            taskDataBuilder
                    .append(line)
                    .append("\n");
        }
        if (linesIterator.hasNext()) {
            historyData = linesIterator.next();
        }
        String taskData = taskDataBuilder.toString();

        FileBackedTaskStore store = Managers.getFileBackedTaskStore();
        FileBackedTaskHistoryManager history = Managers.getFileBackedTaskHistoryManager();


        store.decompress(taskData);
        history.decompress(historyData);

        FileBackedTaskManager manager = new FileBackedTaskManager(file, store, history);
        store.register(manager);
        return manager;
    }

    @Override
    public void update() {
        save();
    }
}
