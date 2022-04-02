package ru.yandex.practicum;

import ru.yandex.practicum.managers.TaskManager;
import ru.yandex.practicum.models.*;
import ru.yandex.practicum.util.Managers;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        testHistory();
    }

    public static void testHistory() {
        TaskManager manager = Managers.getDefaultTaskManager();
        Epic weNeedAtLeastOneEpic = new Epic("Тестирование спсиков", "Печатаем списки");
        manager.createTask(weNeedAtLeastOneEpic);

        for (int i = 0; i < 50; i++) {
            createRandomTask("", "");
        }
        System.out.println("Задачи");
        manager.getAllTasks().forEach(System.out::println);

        for (int i = 0; i < 50; i++) {
            AbstractTask task = getRandomTask();
            System.out.println("Беру задачу " + task);
            manager.getTask(task.getId());
        }
        System.out.println("История");
        manager.history().forEach(System.out::println);
    }

    private static Epic getRandomEpic() {
        List<Epic> epics = Managers.getDefaultTaskManager().getEpics();
        int randomIndex = (int) (Math.random() * epics.size());
        return epics.get(randomIndex);
    }

    private static AbstractTask getRandomTask() {
        List<AbstractTask> tasks = Managers.getDefaultTaskManager().getAllTasks();
        int randomIndex = (int) (Math.random() * tasks.size());
        return tasks.get(randomIndex);
    }

    private static AbstractTask createRandomTask(String title, String description) {
        TaskManager manager = Managers.getDefaultTaskManager();
        int seed = (int) (Math.random() * 30);
        AbstractTask task;
        if (seed < 10) {
            task = new Task(title, description);
        } else if (seed < 20) {
            task = new SubTask(title, description);
            ((SubTask) task).setEpic(getRandomEpic());
        } else {
            task = new Epic(title, description);
        }
        task.add();
        manager.createTask(task);
        return task;
    }
}
