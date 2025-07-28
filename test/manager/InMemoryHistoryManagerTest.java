package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.Taskstatus;

import java.time.Duration;
import java.time.LocalDateTime;
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
        Task task = new Task("Task1", "Кефир, морковь", Taskstatus.NEW, LocalDateTime.now(),
                Duration.ZERO);
        manager.add(task);
        assertTrue(manager.getHistory().contains(task));
    }

    @Test
    public void testRemoveTaskFromHistory() { // Удаление задачи из истории
        Task task = new Task("Task1", "Описание", Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);
        taskManager.addNewTask(task);
        taskManager.deleteTask(task.getId()); // Удаляем задачу из менеджера
        List<Task> history = taskManager.getHistory();
        assertFalse(history.contains(task), "Задача должна быть удалена из истории.");
    }

    @Test
    public void testRemoveFirstNode() {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task1 = new Task("Task1", "Кефир, морковь", Taskstatus.NEW,
                LocalDateTime.now(), Duration.ofHours(1)); //  сейчас
        Task task2 = new Task("Task2", "Описание2", Taskstatus.NEW,
                LocalDateTime.now().plusHours(1).plusMinutes(30), Duration.ofHours(1)); //  через 1 час 30 минут
        Task task3 = new Task("Task3", "Описание3", Taskstatus.NEW,
                LocalDateTime.now().plusHours(3), Duration.ofHours(1)); //  через 3 часа

        // Добавляем задачи в менеджер
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);

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

        // Обратите внимание на время начала задач
        Task task1 = new Task("Task1", "Кефир, морковь", Taskstatus.NEW, LocalDateTime.now(),
                Duration.ofMinutes(10));
        Task task2 = new Task("Task2", "Описание2", Taskstatus.NEW, LocalDateTime.now().plusMinutes(11),
                Duration.ofMinutes(10));

        // Создаем задачи
        taskManager.addNewTask(task1);
        taskManager.addNewTask(task2);

        // Добавляем задачи в историю
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
        assertTrue(history.contains(task1));
        assertTrue(history.contains(task2));

        // Повторное добавление задачи
        historyManager.add(task1); // Добавляем task1 снова

        // Проверяем размер истории
        history = historyManager.getHistory();
        // Задача1 должна быть в истории
        assertTrue(history.contains(task1));
    }


}




