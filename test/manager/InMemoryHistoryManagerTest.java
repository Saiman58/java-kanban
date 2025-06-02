package manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;
import tasks.Subtask;
import tasks.Taskstatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void creatingAnObject() {
        taskManager = Managers.getDefault();
    }


    @Test
    public void testCreateTask() { //Проверка: на наличие задачи
        Task task1 = new Task("Таска1", "Описание таски1", Taskstatus.NEW);
        taskManager.createTask(task1);
        List<Task> tasks1 = taskManager.allTasks();
        assertEquals(1, tasks1.size());
    }

    @Test
    public void addNewTaskAndFindId() { //Проверка: поиск задач
        Task task2 = new Task("Таска2", "Описание таски2", Taskstatus.NEW);
        taskManager.createTask(task2);
        final int taskId = taskManager.getTask(task2.getId()).getId();
        final Task savedTask = taskManager.getTask(taskId);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task2, savedTask, "Задачи не совпадают.");
    }

    @Test
    public void creationEpicInSubtask() { //Проверка: создание Epic в Epic
        Epic epic = new Epic("Отпуск", "Купить билеты");
        taskManager.creatingEpic(epic);
        Subtask subtask = new Subtask(" Subtask4", " описание Subtask4", Taskstatus.NEW, epic.getId());
        boolean added = taskManager.addNewSubtask(subtask) != null;
        assertTrue(added, "Epic нельзя добавить в Epic как подзадачу");
    }

    @Test
    public void subtaskCannotBeItsOwnEpic() { //Создание Subtask как Epic
        Epic epic = new Epic("Название", "Описание");
        taskManager.creatingEpic(epic);
        Subtask subtask = new Subtask("Название Subtask", "Описание Subtask", Taskstatus.NEW,
                epic.getId());
        boolean added = taskManager.addNewSubtask(subtask) != null;
        assertTrue(added, "Subtask не может быть Epic");
    }

    @Test
    public void taskManagerIsInitializedCorrectly() { //Проверка на то что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "TaskManager должен быть инициализирован");
    }

    @Test
    public void testAddingAndFindingTasks() {

        Task task = new Task("Задача", "Описание задачи", Taskstatus.NEW);
        Epic epic = new Epic("Эпик", "Описание эпика");
        Subtask subtask = new Subtask("Подзадача", "Описание подзадачи", Taskstatus.IN_PROGRESS, 2);

        taskManager.createTask(task);
        taskManager.creatingEpic(epic);
        taskManager.addNewSubtask(subtask);

        assertNotNull(taskManager.getTask(task.getId()), "Задача не найдена по ID");
        assertNotNull(taskManager.getEpic(epic.getId()), "Эпик не найден по ID");
        assertNotNull(taskManager.getSubtasksForId(subtask.getId()), "Подзадача не найдена по ID");
    }

    @Test
    public void testUniqueTaskIds() {
        Task task1 = new Task("Task 1", "Описание Task 1", Taskstatus.NEW);
        Task task2 = new Task("Task 2", "Описание Task 2", Taskstatus.NEW);
        Task task3 = new Task("Task 3", "Описание Task 3", Taskstatus.NEW);

        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.createTask(task3);

        int id1 = task1.getId();
        int id2 = task2.getId();
        int id3 = task3.getId();

        assertNotEquals(id1, id2, "ID Task 1 не должен совпадать с ID Task 2");
        assertNotEquals(id1, id3, "ID Task 1 не должен совпадать с ID Task 3");
        assertNotEquals(id2, id3, "ID Task 2 не должен совпадать с ID Task 3");
    }

    @Test
    public void testTaskImmutabilityOnAdd() {
        Task originalTask = new Task("Task", "Описание Task", Taskstatus.NEW);

        taskManager.createTask(originalTask);

        Task retrievedTask = taskManager.getTask(originalTask.getId());

        assertEquals(originalTask.getId(), retrievedTask.getId(), "ID Task изменился.");
        assertEquals(originalTask.getName(), retrievedTask.getName(), "Имя Task изменилось.");
        assertEquals(originalTask.getDescription(), retrievedTask.getDescription(), "Описание Task изменилось.");
        assertEquals(originalTask.getStatus(), retrievedTask.getStatus(), "Статус Task изменился.");
    }

    @Test
    public void testTaskVersioningInHistoryManager() {

        Task originalTask = new Task("Таска", "Описание таски", Taskstatus.NEW);

        taskManager.createTask(originalTask);

        Task updatedTask = new Task(originalTask.getId(), "Обновленная таска", "Новое описание",
                Taskstatus.IN_PROGRESS);
        taskManager.updateTask(updatedTask);

        List<Task> history = taskManager.getHistory();

        assertFalse(history.isEmpty(), "История задач пуста.");

        Task previousVersion = history.get(0);

        assertEquals(originalTask.getId(), previousVersion.getId(), "ID задачи изменился.");
        assertEquals(originalTask.getName(), previousVersion.getName(), "Имя задачи изменилось.");
        assertEquals(originalTask.getDescription(), previousVersion.getDescription(), "Описание задачи изменилось.");
        assertEquals(originalTask.getStatus(), previousVersion.getStatus(), "Статус задачи изменился.");
    }
}

