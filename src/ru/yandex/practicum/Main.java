package ru.yandex.practicum;

import ru.yandex.practicum.managers.TaskManager;
import ru.yandex.practicum.models.*;
import ru.yandex.practicum.util.Managers;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        testDeleteHistory();
    }


    /**
     * Тестирование работы программы
     * После написания менеджера истории проверьте его работу:
     * создайте две задачи, эпик с тремя подзадачами и эпик без подзадач;
     * запросите созданные задачи несколько раз в разном порядке;
     * после каждого запроса выведите историю и убедитесь, что в ней нет повторов;
     * удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться;
     * удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
     * Интересного вам программирования!
     */
    public static void testDeleteHistory() {
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task0 = new Task("", "");
        Task task1 = new Task("", "");
        manager.createTask(task0);
        manager.createTask(task1);
        Epic epic0task = new Epic("", "");
        Epic epic3task = new Epic("", "");
        manager.createTask(epic0task);
        manager.createTask(epic3task);
        SubTask task = new SubTask("", "");
        task.setEpic(epic3task);
        SubTask task2 = new SubTask("", "");
        task2.setEpic(epic3task);
        SubTask task3 = new SubTask("", "");
        task3.setEpic(epic3task);
        manager.createTask(task);
        manager.createTask(task2);
        manager.createTask(task3);

        System.out.println("Задачи");
        manager.getAllTasks().forEach(System.out::println);

        manager.getTask(1);
        manager.getTask(3);
        manager.getTask(4);


        for (int i = 0; i < 10; i++) {
            AbstractTask randomTask = getRandomTask();
            System.out.println("Беру задачу " + randomTask);
            manager.getTask(randomTask.getId());
        }
        System.out.println("История");
        manager.history().forEach(System.out::println);

        manager.removeTask(1);
        manager.removeTask(4);

        System.out.println("История после удаления");
        manager.history().forEach(System.out::println);

    }

    public static void testHistory() {
        TaskManager manager = Managers.getDefaultTaskManager();
        Epic weNeedAtLeastOneEpic = new Epic("Тестирование спсиков", "Печатаем списки");
        manager.createTask(weNeedAtLeastOneEpic);
        System.out.println("weNeedAtLeastOneEpic = " + weNeedAtLeastOneEpic);
        for (int i = 0; i < 10; i++) {
            createRandomTask("", "");
        }
        System.out.println("Задачи");
        manager.getAllTasks().forEach(System.out::println);

        for (int i = 0; i < 10; i++) {
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
