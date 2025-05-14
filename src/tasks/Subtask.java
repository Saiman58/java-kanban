package tasks;

public class Subtask extends Task {
    private int idEpic;
    private int idSubtask;

    public Subtask(String name, String description, Taskstatus taskstatus, int idEpic) {
        super(name, description, taskstatus);
        this.idEpic = idEpic;
        this.idSubtask = 0;
    }

    public Subtask(int idSubtask, int idEpic, String name, String description, Taskstatus taskstatus) {
        super(name, description, taskstatus);
        this.idEpic = idEpic;
        this.idSubtask = idSubtask;
    }

    public int getIdEpic() {
        return idEpic;
    }

    public int getIdSubtask() {
        return idSubtask;
    }

    public void setIdSubtask(int idSubtask) {
        this.idSubtask = idSubtask;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "idSubtask=" + idSubtask +
                ", idEpic=" + idEpic +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
