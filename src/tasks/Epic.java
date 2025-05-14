package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;
    private int id;

    public Epic(String name, String description) {
        super(name, description, Taskstatus.NEW);
        this.subtasks = new ArrayList<>();
    }

    public Epic(int id, int idSubtasks, String name, String description, Taskstatus taskstatus) {
        super(id, name, description, taskstatus);
        this.id = idSubtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id; // Метод для получения ID
    }

    public void updateStatus() {
        if (subtasks.isEmpty() || subtasks.stream().allMatch(subtask -> subtask.getStatus() == Taskstatus.NEW)) {
            setStatus(Taskstatus.NEW);
        } else if (subtasks.stream().allMatch(subtask -> subtask.getStatus() == Taskstatus.DONE)) {
            setStatus(Taskstatus.DONE);
        } else {
            setStatus(Taskstatus.IN_PROGRESS);
        }
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", subtasks=" + subtasks +
                '}';
    }
}
