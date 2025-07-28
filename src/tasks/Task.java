package tasks;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {
    protected int id;
    private String name;
    private String description;
    protected LocalDateTime startTime;
    protected Duration duration;

    public Task(String name, String description, Taskstatus taskstatus, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.description = description;
        this.taskstatus = taskstatus;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(int id, String name, String description, Taskstatus taskstatus, LocalDateTime startTime, Duration duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.taskstatus = taskstatus;
        this.startTime = startTime;
        this.duration = duration;
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime(LocalDateTime startTime, Duration duration) {
        return startTime.plus(duration);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskstatus=" + taskstatus +
                ", startTime=" + startTime +
                ", duration=" + duration +
                '}';
    }
}
