package manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Taskstatus;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class WorkingWithEpicTest {

    private InMemoryTaskManager taskManager;
    private Epic epic;

    @BeforeEach
    public void setUp() {
        taskManager = new InMemoryTaskManager();
        epic = new Epic("Epic1", "Description", LocalDateTime.now(), Duration.ofHours(5));
        taskManager.addNewgEpic(epic);
    }

    @Test
    public void testAllSubtasksNew() { //проверка на статус Epic, в зависимости от статуса Subtask (NEW)
        System.out.println();
        Subtask subtask1 = new Subtask("Subtask1", "описание Subtask1", Taskstatus.NEW, epic.getId(),
                LocalDateTime.now(), Duration.ofHours(2));
        Subtask subtask2 = new Subtask("Subtask2", "описание Subtask2", Taskstatus.NEW, epic.getId(),
                LocalDateTime.now(), Duration.ofHours(20));

        subtask1.setStatus(Taskstatus.NEW);
        subtask2.setStatus(Taskstatus.NEW);
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);

        epic.updateStatus(); // статус Epic
        assertEquals(Taskstatus.NEW, epic.getStatus());
    }

    @Test
    public void testAllSubtasksDone() { //проверка на статус Epic, в зависимости от статуса Subtask (DONE)
        Subtask subtask1 = new Subtask("Subtask1", "описание Subtask1", Taskstatus.DONE, epic.getId(),
                LocalDateTime.now(), Duration.ofHours(2));
        Subtask subtask2 = new Subtask("Subtask2", "описание Subtask2", Taskstatus.DONE, epic.getId(),
                LocalDateTime.now(), Duration.ofHours(20));
        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);

        epic.updateStatus(); // статус Epic
        assertEquals(Taskstatus.DONE, epic.getStatus());
    }

    @Test
    public void testSubtasksNewAndDone() { // ЕСЛИ Subtask с разным статусом, epic должен быть IN_PROGRESS
        Subtask subtask1 = new Subtask("Subtask1", "описание Subtask1", Taskstatus.NEW, epic.getId(),
                LocalDateTime.now(), Duration.ofHours(2));
        Subtask subtask2 = new Subtask("Subtask2", "описание Subtask2", Taskstatus.NEW, epic.getId(),
                LocalDateTime.now(), Duration.ofHours(20));
        Subtask subtask3 = new Subtask("Subtask1", "описание Subtask1", Taskstatus.DONE, epic.getId(),
                LocalDateTime.now(), Duration.ofHours(2));

        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);
        taskManager.addNewSubtask(subtask3);

        epic.updateStatus(); // Обновляем статус Epic
        assertEquals(Taskstatus.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void testAllSubtasksInProgress() { // ЕСЛИ все Subtask со статусом IN_PROGRESS, epic должен быть IN_PROGRESS
        Subtask subtask1 = new Subtask("Subtask1", "описание Subtask1", Taskstatus.IN_PROGRESS,
                epic.getId(),
                LocalDateTime.now(), Duration.ofHours(2));
        Subtask subtask2 = new Subtask("Subtask2", "описание Subtask2", Taskstatus.IN_PROGRESS,
                epic.getId(),
                LocalDateTime.now(), Duration.ofHours(20));

        taskManager.addNewSubtask(subtask1);
        taskManager.addNewSubtask(subtask2);

        epic.updateStatus(); // Обновляем статус Epic
        assertEquals(Taskstatus.IN_PROGRESS, epic.getStatus());
    }
}