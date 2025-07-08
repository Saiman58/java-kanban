package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.nio.file.Files;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        //наполнить данными из файла
        try {
            String fileContent = Files.readString(file.toPath());
            String[] lines = fileContent.split(System.lineSeparator());

            boolean isHistoryLine = false;
            for (String line : lines) {
                if (line.isEmpty()) {
                    isHistoryLine = true;
                    continue;
                }

                if (!isHistoryLine) {
                    Task task = CSVFormatter.fromString(line);
                    if (task != null) {
                        taskManager.getTask(task.getId());
                        // Обновление текущего максимального ID, если необходимо
                        if (task.getId() > generatorId) {
                            generatorId = task.getId();
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return taskManager;
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        return task;
    }

    @Override
    public Task createTask(Task task) {
        Task crearedTask = super.createTask(task);
        return crearedTask;
    }

    @Override
    public Task updateTask(Task task) {
        Task updateTask = super.updateTask(task);
        return updateTask;
    }

    @Override
    public Task deleteTask(int id) {
        Task task = super.deleteTask(id);
        return task;
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    /**
     * Пример формата сохранения:
     * <p>
     * id,type,name,status,description,epic
     * 1,TASK,Task1,NEW,Description task1,
     * 2,EPIC,Epic2,DONE,Description epic2,
     * 3,SUBTASK,Sub Task2,DONE,Description sub task3,2
     */
    protected void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            //1. пишем заголовок
            writer.write(CSVFormatter.getHeader());
            writer.newLine();

            //2. пишем таски
            //2.1 пишем таски
            for (Task task : allTasks()) {
                writer.write(CSVFormatter.toString(task));
                writer.newLine();
            }

            //2.2 пишем сабтаски
            for (Subtask subtask : allSubtasks()) {
                writer.write(CSVFormatter.toString(subtask));
                writer.newLine();
            }

            //2.3 пишем эпики
            for (Epic epic : allEpics()) {
                writer.write(CSVFormatter.toString(epic));
                writer.newLine();
            }

            //3 пишем историю
            writer.write(CSVFormatter.toStringHistory(historyManager));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
