package manager;

import manager.CSVFormatter;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class CSVFormatterTest {

    @Test
    public void testTaskSerialization() {
        Task task = new Task("Test Task", "Description", Taskstatus.NEW,
                LocalDateTime.now(), Duration.ofMinutes(30));

        String serializedTask = CSVFormatter.toString(task);
        Task deserializedTask = CSVFormatter.fromString(serializedTask);

        assertNotNull(deserializedTask);
        assertEquals(task.getId(), deserializedTask.getId());
        assertEquals(task.getName(), deserializedTask.getName());
        assertEquals(task.getDescription(), deserializedTask.getDescription());
        assertEquals(task.getStatus(), deserializedTask.getStatus());
        assertEquals(task.getStartTime(), deserializedTask.getStartTime());
        assertEquals(task.getDuration(), deserializedTask.getDuration());
    }

    @Test
    public void testSubtaskSerialization() {
        Subtask subtask = new Subtask("Test Subtask", "Subtask Description", Taskstatus.NEW,
                1, LocalDateTime.now(), Duration.ofMinutes(15));

        String serializedSubtask = CSVFormatter.toString(subtask);
        Task deserializedSubtask = CSVFormatter.fromString(serializedSubtask);

        assertNotNull(deserializedSubtask);
        assertEquals(subtask.getId(), deserializedSubtask.getId());
        assertEquals(subtask.getName(), deserializedSubtask.getName());
        assertEquals(subtask.getDescription(), deserializedSubtask.getDescription());
        assertEquals(subtask.getStatus(), deserializedSubtask.getStatus());
        assertEquals(subtask.getIdEpic(), ((Subtask) deserializedSubtask).getIdEpic());
        assertEquals(subtask.getStartTime(), deserializedSubtask.getStartTime());
        assertEquals(subtask.getDuration(), deserializedSubtask.getDuration());
    }

    @Test
    public void testEpicSerialization() {
        Epic epic = new Epic("Test Epic", "Epic Description",
                LocalDateTime.now(), Duration.ofHours(1));

        String serializedEpic = CSVFormatter.toString(epic);
        Task deserializedEpic = CSVFormatter.fromString(serializedEpic);

        assertNotNull(deserializedEpic);
        assertEquals(epic.getId(), deserializedEpic.getId());
        assertEquals(epic.getName(), deserializedEpic.getName());
        assertEquals(epic.getDescription(), deserializedEpic.getDescription());
        assertEquals(epic.getStatus(), deserializedEpic.getStatus());
        assertEquals(epic.getStartTime(), deserializedEpic.getStartTime());
        assertEquals(epic.getDuration(), deserializedEpic.getDuration());
    }

    @Test
    public void testInvalidFormat() {
        String invalidLine = "invalid,data,format";
        Task task = CSVFormatter.fromString(invalidLine);
        assertNull(task); // Ожидаем, что метод вернёт null для неверного формата
    }

    @Test
    public void testGetHeader() {
        String header = CSVFormatter.getHeader();
        assertEquals("id,type,name,status,description,epic,priority,createdDate", header);
    }
}