package ru.yandex.practicum.models;

import java.util.List;

final public class SubTask extends AbstractTask {
    private Epic epic;

    public SubTask(String name, String description) {
        super(name, description);
    }

    @Override
    public void setStatus(TaskStatus status) {
        super.setStatus(status);
        epic.evaluateStatus();
    }

    public Epic getEpic() {
        return epic;
    }

    public SubTask setEpic(Epic epic) {
        this.epic = epic;
        return this;
    }

    @Override
    public SubTask add() {
        super.add();
        if (this.epic != null) {
            epic.addSubtask(this);
        }
        return this;
    }

    @Override
    public AbstractTask update() {
        super.update();
        epic.addSubtask(this);
        return this;
    }


    @Override
    public List<AbstractTask> remove() {
        if (this.epic != null) {
            epic.removeSubtask(this);
        }
        return super.remove();
    }
}
