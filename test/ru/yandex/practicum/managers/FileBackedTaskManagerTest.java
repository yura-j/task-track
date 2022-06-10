package ru.yandex.practicum.managers;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.managers.file_backed.FileBackedTaskManager;
import ru.yandex.practicum.util.Managers;

class FileBackedTaskManagerTest extends TaskManagerTest{

    @BeforeEach
    @Override
    void init() {
        Managers.setApplicationMode(ApplicationMode.FILE_BACKING);
        manager = (FileBackedTaskManager) Managers.getDefaultTaskManager();
        super.init();
    }
}