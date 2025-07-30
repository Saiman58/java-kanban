package manager;

import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CSVFormatter {

    public static String getHeader() {
        return "id,type,name,status,description,epic";
    }

    public static String toString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(","); //id
        if (task.getType() == TaskType.SUBTASK) {
            Subtask subtask = (Subtask) task;
            sb.append("SUBTASK,").append(subtask.getName()).append(",")
                    .append(subtask.getStatus()).append(",")
                    .append(subtask.getDescription()).append(",")
                    .append(subtask.getIdEpic()).append(",")
                    .append(subtask.getStartTime()).append(",")
                    .append(subtask.getDuration());
        } else if (task.getType() == TaskType.EPIC) {
            Epic epic = (Epic) task;
            sb.append("EPIC,").append(epic.getName()).append(",")
                    .append(epic.getStatus()).append(",")
                    .append(epic.getDescription()).append(",");
        } else {
            sb.append("TASK,").append(task.getName()).append(",")
                    .append(task.getStatus()).append(",")
                    .append(task.getDescription()).append(",").append(task.getStartTime())
                    .append(",").append(task.getDuration());
        }
        return sb.toString();
    }

    public static String toStringHistory(HistoryManager historyManager) {
        StringBuilder sb = new StringBuilder();
        for (Task task : historyManager.getHistory()) {
            sb.append(task.getId()).append(",");
        }
        return sb.toString();
    }

    public static Task fromString(String line) {
        //пользуемся split
        //парсим значение в массиве, который получается после сплита
        //в зависимости от типа таски создать соответсвующий объект
        String[] parts = line.split(",");
        if (parts.length < 5) return null; // Неверный формат

        int id = Integer.parseInt(parts[0]);
        String type = parts[1];
        String name = parts[2];
        Taskstatus status = Taskstatus.valueOf(parts[3]);
        String description = parts[4];
        int epicId = parts.length > 5 ? Integer.parseInt(parts[5]) : -1;
        String startTimeString = parts[6];
        String durationString = parts[7];

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(startTimeString, formatter);
        Duration duration = Duration.parse(durationString);


        switch (type) {
            case "TASK":
                return new Task(id, name, description, status, startTime, duration);
            case "EPIC":
                return new Epic(id, epicId, name, description, startTime, duration);
            case "SUBTASK":
                return new Subtask(id, epicId, name, description, status, startTime, duration);
            default:
                return null; // Неизвестный тип
        }

    }


}


