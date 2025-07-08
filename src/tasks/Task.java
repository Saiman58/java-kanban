package tasks;

public class Task {
    protected int id;
    private String name;
    private String description;

    public Task(String name, String description, Taskstatus taskstatus) {
        this.name = name;
        this.description = description;
        this.taskstatus = taskstatus;
    }

    public Task(int id, String name, String description, Taskstatus taskstatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.taskstatus = taskstatus;
    }

    private Taskstatus taskstatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Taskstatus getStatus() {
        return taskstatus;
    }

    public void setStatus(Taskstatus taskstatus) {
        this.taskstatus = taskstatus;
    }

    public TaskType getType() { //проверка на тип задачи
        return TaskType.TASK;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskstatus=" + taskstatus +
                '}';
    }
}
