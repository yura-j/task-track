import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        testManager();
    }

    public static void testManager() {
        TaskManager manager = new TaskManager();
        Task taskTesting = new Task("Тестирование", "Создать в классе Main метод testManager");
        Task taskTestImplementing = new Task("Реализация", "Метод testManager должен создать" +
                " 2 задачи," +
                " 2 подзадачи засунутые в один эпик" +
                " и эпик с одной подзадачей");
        SubTask subTaskTestTaskList = new SubTask("Тестирование геттера списка задач",
                "Распечатайте списки задач, через System.out.println(..)");
        SubTask subTaskTestOtherList = new SubTask("Тестирование геттера других типов задач",
                "Распечатайте списки эпиков и подзадач, через System.out.println(..)");
        Epic epicTestTakLists = new Epic("Тестирование спсиков", "Печатаем списки");
        SubTask subTaskTestStatusLogic = new SubTask("Тестирование корректной обработки статусов",
                "Измените статусы созданных объектов, распечатайте." +
                        " Проверьте, что статус задачи и подзадачи сохранился," +
                        " а статус эпика рассчитался по статусам подзадач.");
        Epic epicTestDeleteEpic = new Epic("Тестирование удаления", "И, наконец, попробуйте удалить одну из задач и один из эпиков.");

        taskTesting.add();
        taskTestImplementing.add();

        epicTestTakLists.add();
        epicTestDeleteEpic.add();

        subTaskTestTaskList
                .setEpic(epicTestTakLists)
                .add();
        subTaskTestOtherList
                .setEpic(epicTestTakLists)
                .add();
        subTaskTestStatusLogic
                .setEpic(epicTestDeleteEpic)
                .add();


        ArrayList<Epic> epics = manager.getEpics();
        System.out.println("epics = " + epics);

        ArrayList<Task> tasks = manager.getTasks();
        System.out.println("tasks = " + tasks);

        ArrayList<SubTask> subTasks = manager.getSubTasks();
        System.out.println("subTasks = " + subTasks);

        System.out.println("----------------");
        System.out.println("Меняю статус подзадачи на IN_PROGRESS, ожидаю IN_PROGRESS У эпика");
        SubTask subtaskWithOtherStatus = new SubTask();
        subTaskTestTaskList.replicateMeTo(subtaskWithOtherStatus);
        subTaskTestTaskList = null;
        subtaskWithOtherStatus.setEpic(epicTestTakLists);
        subtaskWithOtherStatus.setStatus(TaskStatus.IN_PROGRESS);
        manager.updateTask(subtaskWithOtherStatus);


        System.out.println("epicTestTakLists = " + epicTestTakLists);

        System.out.println("----------------");
        System.out.println("Меняю статус подзадачи на DONE, ожидаю IN_PROGRESS У эпика");
        subtaskWithOtherStatus.setStatus(TaskStatus.DONE);
        System.out.println("epicTestTakLists = " + epicTestTakLists);

        System.out.println("----------------");
        System.out.println("Меняю второй статус подзадачи на DONE, ожидаю DONE У эпика");
        subTaskTestOtherList.setStatus(TaskStatus.DONE);
        System.out.println("epicTestTakLists = " + epicTestTakLists);

        System.out.println("----------------");
        System.out.println("Удаляю эпик \"Тестирование удаления\"");
        manager.removeTask(epicTestDeleteEpic.getId());
        System.out.println("epics = " + manager.getEpics());
        System.out.println("----------------");
        System.out.println("Удаляю задачу \"Тестирование\"");
        manager.removeTask(taskTesting.getId());

        System.out.println("tasks = " + manager.getTasks());

        System.out.println("----------------");
        System.out.println("Удаляю все задачи");
        manager.removeTasks();
        System.out.println("epics = " + manager.getEpics());
        System.out.println("tasks = " + manager.getTasks());
        System.out.println("subtasks = " + manager.getSubTasks());

    }
}
