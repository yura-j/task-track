package ru.yandex.practicum.managers;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.managers.in_memory.InMemoryTaskManager;
import ru.yandex.practicum.util.Managers;

class InMemoryTaskManagerTest extends TaskManagerTest{

    @BeforeEach
    @Override
    void init() {
        Managers.setApplicationMode(ApplicationMode.IN_MEMORY);
        manager = (InMemoryTaskManager) Managers.getDefaultTaskManager();
        super.init();
    }
}