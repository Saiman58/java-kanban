package manager;

import exceptions.ManagerSaveException;

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

    public static FileBackedTaskManager loadFromFile(File file) {
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
                        if (task.getId() > taskManager.generatorId) {
                            taskManager.generatorId = task.getId();
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла: " + e.getMessage());
        }

        return taskManager;
    }

    //Task
    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        return task;
    }


    @Override
    public Task addNewTask(Task task) {
        Task crearedTask = super.addNewTask(task);
        save();
        return crearedTask;
    }

    @Override
    public Task updateTask(Task task) {
        Task updateTask = super.updateTask(task);
        save();
        return updateTask;
    }

    @Override
    public Task deleteTask(int id) {
        Task task = super.deleteTask(id);
        save();
        return task;
    }

    @Override
    public void deleteTasks() {
        super.deleteTasks();
        save();
    }

    //Subtask
    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        Subtask createdSubtask = super.addNewSubtask(subtask);
        save(); // Сохраняем данные после добавления подзадачи
        return createdSubtask;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) {
        Subtask updateSubtask = super.updateSubtask(subtask);
        save();
        return updateSubtask;
    }

    @Override
    public Subtask deleteSubtask(int id) {
        Subtask subtask = super.deleteSubtask(id);
        save(); // Сохраняем данные после удаления подзадачи
        return subtask;
    }

    //Epic
    @Override
    public Epic addNewgEpic(Epic epic) {
        Epic createdEpic = super.addNewgEpic(epic);
        save(); // Сохраняем данные после добавления эпика
        return createdEpic;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        Epic updatedEpic = super.updateEpic(epic);
        save(); // Сохраняем данные после обновления эпика
        return updatedEpic;
    }

    @Override
    public Epic deleteEpicToId(int id) {
        Epic epic = super.deleteEpicToId(id);
        save(); // Сохраняем данные после удаления эпика
        return epic;
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
            throw new ManagerSaveException("Ошибка при записи в  файл: " + e.getMessage());


        }
    }
}
