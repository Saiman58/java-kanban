package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {
    private int idEpic;

    public Subtask(String name, String description, Taskstatus taskstatus, int idEpic,
                   LocalDateTime startTime, Duration duration) {
        super(name, description, taskstatus, startTime, duration);
        this.idEpic = idEpic;
    }

    public Subtask(int idSubtask, int idEpic, String name, String description, Taskstatus taskstatus,
                   LocalDateTime startTime, Duration duration) {
        super(idSubtask, name, description, taskstatus, startTime, duration);
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
                ", startTime=" + getStartTime() +
                ", duration=" + getDuration() +
                '}';
    }


}
