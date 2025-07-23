package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.Taskstatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WorkingWithTaskTest {

    TaskManager taskManager;

    @BeforeEach
    public void creatingAnObject() {
        taskManager = Managers.getDefault();
    }

    @Test
    public void testCreateTask() { //Проверка: на наличие задачи
        Task task1 = new Task("Task1", "Кефир, морковь", Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);
        taskManager.addNewTask(task1);
        List<Task> tasks1 = taskManager.allTasks();
        assertEquals(1, tasks1.size());
    }

    @Test
    public void addNewTaskAndFindId() { //Проверка: поиск задач
        Task task2 = new Task("Task", "Кефир, морковь", Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);
        taskManager.addNewTask(task2);
        final int taskId = taskManager.getTask(task2.getId()).getId();
        final Task savedTask = taskManager.getTask(taskId);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task2, savedTask, "Задачи не совпадают.");
    }

    @Test
    public void testAddingAndFindingTasks() {

        Task task = new Task("Task1", "Кефир, морковь", Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);
        Epic epic = new Epic("Epic1", "Description", LocalDateTime.now(), Duration.ofHours(5));
        Subtask subtask = new Subtask("Subtask1", "описание Subtask1", Taskstatus.NEW, epic.getId(),
                LocalDateTime.now(), Duration.ofHours(2));

        taskManager.addNewTask(task);
        taskManager.addNewgEpic(epic);
        taskManager.addNewSubtask(subtask);

        assertNotNull(taskManager.getTask(task.getId()), "Задача не найдена по ID");
        assertNotNull(taskManager.getEpic(epic.getId()), "Эпик не найден по ID");
        assertNotNull(taskManager.getSubtasksForId(subtask.getId()), "Подзадача не найдена по ID");
    }

    @Test
    public void testUniqueTaskIds() {
        Task task1 = new Task("Task1", "Описание1", Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);
        Task task2 = new Task("Task1", "Описание2", Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);
        Task task3 = new Task("Task1", "Описание3", Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);

        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);
        taskManager.addNewTask(task3);

        int id1 = task1.getId();
        int id2 = task2.getId();
        int id3 = task3.getId();

        assertNotEquals(id1, id2, "ID Task 1 не должен совпадать с ID Task 2");
        assertNotEquals(id1, id3, "ID Task 1 не должен совпадать с ID Task 3");
        assertNotEquals(id2, id3, "ID Task 2 не должен совпадать с ID Task 3");
    }

    @Test
    public void testTaskImmutabilityOnAdd() {
        Task originalTask = new Task("Task1", "Описание1", Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);

        taskManager.addNewTask(originalTask);

        Task retrievedTask = taskManager.getTask(originalTask.getId());

        assertEquals(originalTask.getId(), retrievedTask.getId(), "ID Task изменился.");
        assertEquals(originalTask.getName(), retrievedTask.getName(), "Имя Task изменилось.");
        assertEquals(originalTask.getDescription(), retrievedTask.getDescription(), "Описание Task изменилось.");
        assertEquals(originalTask.getStatus(), retrievedTask.getStatus(), "Статус Task изменился.");
    }

    @Test
    public void testTaskVersioningInHistoryManager() {

        Task originalTask = new Task("Task1", "Описание1", Taskstatus.NEW,
                LocalDateTime.now(), Duration.ZERO);

        taskManager.addNewTask(originalTask);

        Task updatedTask = new Task(originalTask.getId(), "Новое имя таски", "Обновленная таска",
                Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);

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
