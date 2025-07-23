package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Subtask> subtasks;
    private Duration duration;
    LocalDateTime startTime;

    public Epic(String name, String description, LocalDateTime startTime, Duration duration) {
        super(name, description, Taskstatus.NEW, startTime, duration);
        this.subtasks = new ArrayList<>();
        this.duration = duration;
        this.startTime = startTime;
    }

    public Epic(int epicId, int idSubtasks, String name, String description, LocalDateTime startTime, Duration duration) {
        super(epicId, name, description, Taskstatus.NEW, startTime, duration);
        this.id = idSubtasks;
        this.subtasks = new ArrayList<>();
        this.duration = duration;
        this.startTime = startTime;

    }

    @Override
    public TaskType getType() {   //переопределение на проверку типа задачи
        return TaskType.EPIC;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.add(subtask);
    }

    public ArrayList<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id; // Метод для получения ID
    }

    public void updateStatus() { // обновление статуса
        if (subtasks.isEmpty() || subtasks.stream().allMatch(subtask -> subtask.getStatus() == Taskstatus.NEW)) {
            setStatus(Taskstatus.NEW);
        } else if (subtasks.stream().allMatch(subtask -> subtask.getStatus() == Taskstatus.DONE)) {
            setStatus(Taskstatus.DONE);
        } else {
            setStatus(Taskstatus.IN_PROGRESS);
        }
    }


    // Методы для расчёта duration, startTime и endTime
    public Duration calculateDuration() {
        Duration totalDuration = Duration.ZERO;
        for (Subtask subtask : subtasks) {
            totalDuration = totalDuration.plus(subtask.getDuration());
        }
        return totalDuration;
    }

    public LocalDateTime calculateStartTime() {
        LocalDateTime minStartTime = null;
        for (Subtask subtask : subtasks) {
            if (minStartTime == null || subtask.getStartTime().isBefore(minStartTime)) {
                minStartTime = subtask.getStartTime();
            }
        }
        return minStartTime;
    }

    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                ", startTime=" + getStartTime() +
                ", duration=" + getDuration() +
                ", subtasks=" + subtasks +
                '}';
    }
}
