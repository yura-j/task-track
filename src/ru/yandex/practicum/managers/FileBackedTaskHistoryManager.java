package ru.yandex.practicum.managers;

import ru.yandex.practicum.util.Compressible;
import ru.yandex.practicum.util.Managers;

import java.util.Arrays;

public class FileBackedTaskHistoryManager extends InMemoryTaskHistoryManager implements Compressible {

    TaskStore store = Managers.getDefaultStore();

    @Override
    public String compress() {
        String[] ids = super
                .getHistory()
                .stream()
                .map(task -> String.valueOf(task.getId()))
                .toArray(String[]::new);
        return String.join(",", ids);
    }

    @Override
    public void decompress(String compressedData) {
        Arrays.stream(compressedData.split(","))
                .map(Integer::parseInt)
                .map(store::getTask)
                .forEach(this::add);
    }
}
