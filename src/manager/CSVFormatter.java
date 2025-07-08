package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.Taskstatus;

public class CSVFormatter {

    public static String getHeader() {
        return "id,type,name,status,description,epic";
    }

    public static String toString(Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append(task.getId()).append(","); //id
        if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            sb.append("SUBTASK,").append(subtask.getName()).append(",")
                    .append(subtask.getStatus()).append(",")
                    .append(subtask.getDescription()).append(",")
                    .append(subtask.getIdEpic());
        } else if (task instanceof Epic) {
            Epic epic = (Epic) task;
            sb.append("EPIC,").append(epic.getName()).append(",")
                    .append(epic.getStatus()).append(",")
                    .append(epic.getDescription()).append(",");
        } else {
            sb.append("TASK,").append(task.getName()).append(",")
                    .append(task.getStatus()).append(",")
                    .append(task.getDescription()).append(",");
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

        switch (type) {
            case "TASK":
                return new Task(id, name, description, status);
            case "EPIC":
                return new Epic(id, epicId, name, description);
            case "SUBTASK":
                return new Subtask(id, epicId, name, description, status);
            default:
                return null; // Неизвестный тип
        }
    }
}


