package manager;

import tasks.Epic;
import tasks.Task;
import tasks.Subtask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {  //Диспетчер задач в памяти
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private int generatorId = 1;
    private HistoryManager historyManager = Managers.getDefaultHistory();

    //методы для Task
    @Override
    public Task createTask(Task task) { //создание Task
        task.setId(getNewId());
        tasks.put(task.getId(), task);
        addHistory(task);
        return task;
    }

    private int getNewId() { // генератор id
        return generatorId++;
    }

    @Override
    public Task updateTask(Task task) { //обновление Task
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public Task getTask(int id) { // поиска Task по id
        Task task = tasks.get(id);
        addHistory(task);//рефакторинг метода
        return task;
    }

    @Override
    public Task deleteTask(int id) { //удаление Task по id
        Task task = tasks.remove(id);
        if (task != null) {
            historyManager.remove(id);
        }
        return task;
    }

    @Override
    public void deleteTasks() { // удаление всех Task
        for (Integer id : new ArrayList<>(tasks.keySet())) {
            historyManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public ArrayList<Task> allTasks() {
        return new ArrayList<>(tasks.values());
    }

    //методы для Subtask
    @Override
    public Subtask addNewSubtask(Subtask subtask) { //создание Subtask
        Epic epic = epics.get(subtask.getIdEpic());
        if (epic == null) {
            throw new IllegalArgumentException("Для данной подзадачи не найден Epic!");
        }
        subtask.setId(getNewId());
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask);
        epic.updateStatus(); // обновляем статус Subtask
        return subtask;
    }

    @Override
    public ArrayList<Subtask> allSubtasks() { // показать все Subtask
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Subtask getSubtasksForId(int id) { // поиск Subtask по id
        Subtask subtask = subtasks.get(id);
        addHistory(subtask); //рефакторинг метода
        return subtask;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask) { // обновление Subtask
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getIdEpic());
        if (epic != null) {
            epic.updateStatus(); // обновляем статус Epic
        }
        return subtask;
    }

    @Override
    public Subtask deleteSubtask(int idSubtask) { // удалить Subtask
        Subtask subtask = subtasks.remove(idSubtask);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getIdEpic());
            if (epic != null) {
                epic.updateStatus(); // Обновляем статус Epic
            }
            historyManager.remove(idSubtask);
        }
        return subtask;
    }

    @Override
    public void deleteAllSubtask() { // удаление всех subtask
        for (Subtask subtask : new ArrayList<>(subtasks.values())) {
            historyManager.remove(subtask.getId());
        }

        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            epic.updateStatus();
        }
        subtasks.clear();
    }

    //методы для Epic
    @Override
    public Epic creatingEpic(Epic epic) { //создание Epic
        epic.setId(getNewId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Epic updateEpic(Epic epic) { //обновление Epic
        epics.put(epic.getId(), epic);
        return epic;
    }

    @Override
    public Epic getEpic(int id) { // поиск Subtask по id
        Epic epic = epics.get(id);
        addHistory(epic); //рефакторинг метода
        return epic;
    }

    @Override
    public ArrayList<Epic> allEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public Epic deleteEpicToId(int id) { //удаление Epic по id
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());
                historyManager.remove(subtask.getId());
            }
        }
        return epic;
    }

    @Override
    public void deleteEpic() { // удаление всех Epic
        for (Epic epic : new ArrayList<>(epics.values())) {
            historyManager.remove(epic.getId());
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void addHistory(Task task) {
        historyManager.add(task);
    }

}
