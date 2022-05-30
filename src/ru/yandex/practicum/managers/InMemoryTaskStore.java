package ru.yandex.practicum.managers;

import ru.yandex.practicum.exceptions.TimeTableBusyException;
import ru.yandex.practicum.models.AbstractTask;
import ru.yandex.practicum.util.Managers;

import java.util.*;

public class InMemoryTaskStore implements TaskStore {
    protected final HashMap<Integer, AbstractTask> taskList = new HashMap<>();
    protected final Set<AbstractTask> prioritizedTasks = new TreeSet<>();
    protected int maxId = 0;
    protected TimeTable schedule = Managers.getTimeTable();

    @Override
    public ArrayList<AbstractTask> getTasks() {
        return new ArrayList<>(taskList.values());
    }

    @Override
    public Set<AbstractTask> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    @Override
    public AbstractTask getTask(Integer id) {
        return taskList.get(id);
    }


    @Override
    public void addTask(AbstractTask task) {
        try {
            task.addToTimeTable(schedule);
        } catch (TimeTableBusyException e) {
            System.out.println(e.getMessage());
            return;
        }
        taskList.put(task.getId(), task);
        prioritizedTasks.remove(task);
        prioritizedTasks.add(task);
    }

    @Override
    public void removeTask(int id) {
        AbstractTask task = taskList.get(id);
        if (task != null) {
            task.removeFromTimeTable(schedule);
            prioritizedTasks.remove(task);
            taskList.remove(id);
        }
    }

    @Override
    public void removeTasks() {
        Managers.resetTimeTable();
        prioritizedTasks.clear();
        taskList.clear();
        maxId = 0;
    }

    @Override
    public int generateNewId() {
        return (++maxId);
    }
}
