package manager;

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
    public void testAddTaskToHistory() { //Добавления задачи в историю
        InMemoryHistoryManager manager = new InMemoryHistoryManager();
        Task task = new Task("Таска", "Описание", Taskstatus.NEW);
        manager.add(task);
        assertTrue(manager.getHistory().contains(task));
    }

    @Test
    public void testRemoveTaskFromHistory() { // Удаление задачи из истории
        Task task = new Task("Таска22", "Описание", Taskstatus.NEW);
        taskManager.createTask(task);
        taskManager.deleteTask(task.getId()); // Удаляем задачу из менеджера
        List<Task> history = taskManager.getHistory();
        assertFalse(history.contains(task), "Задача должна быть удалена из истории.");
    }

    @Test
    public void testRemoveFirstNode() {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        // Создаем несколько задач
        Task task1 = new Task("Task1", "Описание Task 1", Taskstatus.NEW);
        Task task2 = new Task("Task2", "Описание Task 2", Taskstatus.NEW);
        Task task3 = new Task("Task3", "Описание Task 3", Taskstatus.NEW);

        // Добавляем задачи в менеджер
        manager.createTask(task1);
        manager.createTask(task2);
        manager.createTask(task3);

        // Проверяем, что задачи добавлены
        assertEquals(3, manager.allTasks().size(), "История должна содержать 3 задачи.");
        assertTrue(manager.getHistory().contains(task1), "История должна содержать task1.");
        assertTrue(manager.getHistory().contains(task2), "История должна содержать task2.");
        assertTrue(manager.getHistory().contains(task3), "История должна содержать task3.");

        // Удаление задач
        manager.deleteTask(task1.getId());
        manager.deleteTask(task3.getId());

        // Проверяем, что задачи удалены
        assertEquals(1, manager.allTasks().size(), "История должна содержать 1 задачу после удаления.");
        assertFalse(manager.getHistory().contains(task1), "История не должна содержать task1.");
        assertTrue(manager.getHistory().contains(task2), "История должна содержать task2.");
        assertFalse(manager.getHistory().contains(task3), "История не должна содержать task3.");
    }

    @Test
    public void testRepeatedViews() {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();

        Task task1 = new Task("Task1", "Task 1", Taskstatus.NEW);
        Task task2 = new Task("Task2", "Task 2", Taskstatus.NEW);

        // Создаем задачи
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        // Добавляем задачи в историю
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertTrue(history.contains(task1));
        assertTrue(history.contains(task2));

        // Повторное добавление задачи
        historyManager.add(task1);

        // Проверяем размер истории
        history = historyManager.getHistory();
        // Задача1 должна быть в истории
        assertTrue(history.contains(task1));
    }


}




