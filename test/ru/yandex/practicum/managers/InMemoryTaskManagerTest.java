package ru.yandex.practicum.managers;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.util.Managers;

class InMemoryTaskManagerTest extends TaskManagerTest{

    @BeforeEach
    @Override
    void init() {
        manager = (InMemoryTaskManager) Managers.getDefaultTaskManager();
        Managers.setApplicationMode(ApplicationMode.IN_MEMORY);
        super.init();
    }
}