import java.util.ArrayList;

abstract public class AbstractTask {
    protected int id = 0;
    protected String name = "";
    protected String description = "";
    protected TaskStatus status = TaskStatus.NEW;
    protected TaskStore store = TaskStore.getInstance();

    public AbstractTask() {

    }

    public AbstractTask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public AbstractTask replicateMeTo(AbstractTask task) {
        task.id = id;
        task.name = name;
        task.description = description;
        task.status = status;
        task.store = store;
        return task;
    }

    protected ArrayList<AbstractTask> filterTypedElements() {
        ArrayList<AbstractTask> list = new ArrayList<>();
        for (AbstractTask task : store.getTasks()) {
            if (task.getClass() == this.getClass()) {
                list.add(task);
            }
        }
        return list;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public AbstractTask add() {
        this.id = store.generateNewId();
        this.store.addTask(this);
        return this;
    }

    public AbstractTask update() {
        store.addTask(this);
        return this;
    }

    public AbstractTask remove() {
        store.removeTask(this.id);
        return this;
    }

    @Override
    public String toString() {
        return name + "@" + status;
    }
}
