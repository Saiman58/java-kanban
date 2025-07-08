package tasks;

public class Subtask extends Task {
    private int idEpic;

    public Subtask(String name, String description, Taskstatus taskstatus, int idEpic) {
        super(name, description, taskstatus);
        this.idEpic = idEpic;
    }

    public Subtask(int idSubtask, int idEpic, String name, String description, Taskstatus taskstatus) {
        super(idSubtask, name, description, taskstatus);
        this.idEpic = idEpic;
    }

    @Override
    public TaskType getType() {  //переопределение на проверку типа задачи
        return TaskType.SUBTASK;
    }

    public int getIdEpic() {
        return idEpic;
    }


    @Override
    public String toString() {
        return "Subtask{" +
                "idEpic=" + idEpic +
                ", id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
