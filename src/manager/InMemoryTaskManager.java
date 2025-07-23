package manager;

import tasks.Epic;
import tasks.Task;
import tasks.Subtask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {  //Диспетчер задач в памяти
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, Subtask> subtasks = new HashMap<>();
    protected int generatorId = 1;
    protected HistoryManager historyManager = Managers.getDefaultHistory();

    //методы для Task
    @Override
    public Task addNewTask(Task task) { //создание Task
        if (hasOverlappingTasks(task)){
            throw new IllegalArgumentException("Задача пересекается с существующими задачами");
        }
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
        if (hasOverlappingTasks(task)) {
            throw new IllegalArgumentException("Обновлённая задача пересекается с существующими задачами!");
        }
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
        tasks.keySet().forEach(historyManager::remove); //рефакторинг метода (замена на Stream API)
        tasks.clear();
    }

    @Override
    public ArrayList<Task> allTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public LocalDateTime getEndTime(LocalDateTime startTime, Duration duration) {
        return startTime.plus(duration);
    }


    //методы для Subtask
    @Override
    public Subtask addNewSubtask(Subtask subtask) { //создание Subtask
        if (hasOverlappingTasks(subtask)){
            throw new IllegalArgumentException("Подзадача пересекается с существующими задачами");
        }
        Epic epic = epics.get(subtask.getIdEpic());
        if (epic == null) {
            throw new IllegalArgumentException("Для данной подзадачи не найден Epic!");
        }
        subtask.setId(getNewId());
        subtasks.put(subtask.getId(), subtask);
        epic.addSubtask(subtask);
        epic.updateStatus(); // обновляем статус Subtask
        Duration totalDuration = epic.calculateDuration();
        epic.setDuration(totalDuration); // расчёт продолжительности времени Epic
        LocalDateTime calculateStartTime = epic.calculateStartTime();
        epic.setStartTime(calculateStartTime); // расчёт старта времени выполнения Epic
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
        if (hasOverlappingTasks(subtask)){
            throw new IllegalArgumentException("Подзадача пересекается с существующими задачами");
        }
        subtasks.put(subtask.getId(), subtask);
        Epic epic = epics.get(subtask.getIdEpic());
        if (epic != null) {
            epic.updateStatus(); // обновляем статус Epic
            Duration totalDuration = epic.calculateDuration();
            epic.setDuration(totalDuration); // расчёт продолжительности времени Epic
            LocalDateTime calculateStartTime = epic.calculateStartTime();
            epic.setStartTime(calculateStartTime); // расчёт старта времени выполнения Epic
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
                Duration totalDuration = epic.calculateDuration();
                epic.setDuration(totalDuration); // расчёт продолжительности времени Epic
                LocalDateTime calculateStartTime = epic.calculateStartTime();
                epic.setStartTime(calculateStartTime); // расчёт старта времени выполнения Epic
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
            Duration totalDuration = epic.calculateDuration();
            epic.setDuration(totalDuration); // расчёт продолжительности времени Epic
            LocalDateTime calculateStartTime = epic.calculateStartTime();
            epic.setStartTime(calculateStartTime); // расчёт старта времени выполнения Epic
        }
        subtasks.clear();
    }

    //методы для Epic
    @Override
    public Epic addNewgEpic(Epic epic) { //создание Epic
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

    public Set<Task> getPrioritizedTasks() { // сортировка задач
        List<Task> prioritizedTasks = new ArrayList<>();
        //TreeMap для хранения задач по startTime
        Map<LocalDateTime, List<Task>> tasksByStartTime = new TreeMap<>();

        //добавляем все задачи и подзадачи в TreeMap
        for (Task task : tasks.values()) {
            LocalDateTime startTime = task.getStartTime();
            tasksByStartTime.putIfAbsent(startTime, new ArrayList<>());
            tasksByStartTime.get(startTime).add(task);
        }
        //задачи в порядке приоритета
        for (List<Task> taskList : tasksByStartTime.values()) {
            prioritizedTasks.addAll(taskList);
        }
        return new HashSet<>(prioritizedTasks); // Преобразуем список в множество
    }

    public boolean isOverlapping(Task task1, Task task2) {
        LocalDateTime start1 = task1.getStartTime();
        LocalDateTime end1 = start1.plus(task1.getDuration());
        LocalDateTime start2 = task2.getStartTime();
        LocalDateTime end2 = start2.plus(task2.getDuration());

        return !(end1.isBefore(start2) || end2.isBefore(start1));
    }

    public boolean hasOverlappingTasks(Task newTask) {
        return tasks.values().stream()
                .anyMatch(task -> isOverlapping(newTask, task));
    }




    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void addHistory(Task task) {
        historyManager.add(task);
    }

}
