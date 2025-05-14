import tasks.Epic;
import tasks.Task;
import tasks.Subtask;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorId = 1;
    private int generatorIdSubtask = 1;

    //методы для Task
    public Task createTask(Task task) { //создание Task
        task.setId(getNewtId());
        tasks.put(task.getId(), task);
        return task;
    }

    private int getNewtId() { // генератор id
        return generatorId++;
    }

    private int getNewIdSubtask() { //генератор id Subtask
        return generatorIdSubtask++;
    }

    public Task updateTask(Task task) { //обновление Task
        tasks.put(task.getId(), task);
        return task;
    }

    public Task getTask(int id) { // поиска Task по id
        return tasks.get(id);
    }

    public Task deleteTask(int id) { //удаление Task по id
        return tasks.remove(id);
    }

    public void deleteTasks() { // удаление всех Task
        tasks.clear();
    }

    public ArrayList<Task> allTasks() {
        return new ArrayList<>(tasks.values());
    }

    //методы для Subtask
    public Subtask addNewSubtask(Subtask subtask) { //создание Subtask
        subtask.setIdSubtask(getNewIdSubtask());
        subtasks.put(subtask.getIdSubtask(), subtask);
        Epic epic = epics.get(subtask.getIdEpic());
        if (epic != null) {
            epic.addSubtask(subtask);
            epic.updateStatus(); // обновляем статус Subtask
        }
        return subtask;
    }

    public ArrayList<Subtask> allSubtasks() { // показать все Subtask
        return new ArrayList<>(subtasks.values());
    }

    public Subtask getSubtasksForId(int id) { // поиск Subtask по id
        return subtasks.get(id);
    }

    public Subtask updateSubtask(Subtask subtask) { // обновление Subtask
        subtasks.put(subtask.getIdSubtask(), subtask);
        Epic epic = epics.get(subtask.getIdEpic());
        if (epic != null) {
            epic.updateStatus(); // обновляем статус Epic
        }
        return subtask;
    }

    public Subtask deleteSubtask(int idSubtask) { // удалить Subtask
        Subtask subtask = subtasks.remove(idSubtask);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getIdEpic());
            if (epic != null) {
                epic.updateStatus(); // Обновляем статус Epic
            }
        }
        return subtask;
    }

    //методы для Epic
    public Epic creatingEpic(Epic epic) {
        epic.setId(getNewtId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Epic updateEpic(Epic epic) { //обновление Epic
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Epic getEpic(int id) {
        return epics.get(id);
    }

    public ArrayList<Epic> allEpics() {
        return new ArrayList<>(epics.values());
    }

    public Epic deleteEpicToId(int id) { //удаление Epic по id
        return epics.remove(id);
    }

    public void deleteEpic() { // удаление всех тасок
        epics.clear();
    }
}
