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

    //методы для Task
    public Task createTask(Task task) { //создание Task
        task.setId(getNewId());
        tasks.put(task.getId(), task);
        return task;
    }

    private int getNewId() { // генератор id
        return generatorId++;
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
        Epic epic = epics.get(subtask.getIdEpic());
        if (epic == null) {
            System.out.println("Для данной подзадачи не найден Epic!");
            return null;
        }
        subtask.setId(getNewId());
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask);
        epic.updateStatus(); // обновляем статус Subtask
        return subtask;
    }

    public ArrayList<Subtask> allSubtasks() { // показать все Subtask
        return new ArrayList<>(subtasks.values());
    }

    public Subtask getSubtasksForId(int id) { // поиск Subtask по id
        return subtasks.get(id);
    }

    public Subtask updateSubtask(Subtask subtask) { // обновление Subtask
        subtasks.put(subtask.getId(), subtask);
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

    public void deleteAllSubtask() { // удаление всех subtask
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            epic.updateStatus();
        }
        subtasks.clear();
    }

    //методы для Epic
    public Epic creatingEpic(Epic epic) {
        epic.setId(getNewId());
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
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
        }
        return epic;
    }

    public void deleteEpic() { // удаление всех Epic
        epics.clear();
        subtasks.clear();
    }
}
