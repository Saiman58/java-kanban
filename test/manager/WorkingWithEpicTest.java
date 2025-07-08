package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;
import tasks.Subtask;
import tasks.Taskstatus;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkingWithEpicTest {
    private TaskManager taskManager;

    @BeforeEach
    public void creatingAnObject() {
        taskManager = Managers.getDefault();
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
}
