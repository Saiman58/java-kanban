package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    //методы для Task
    Task addNewTask(Task task);

    Task updateTask(Task task);

    Task getTask(int id);

    Task deleteTask(int id);

    void deleteTasks();

    ArrayList<Task> allTasks();


    //методы для Subtask
    Subtask addNewSubtask(Subtask subtask);

    ArrayList<Subtask> allSubtasks();

    Subtask getSubtasksForId(int id);

    Subtask updateSubtask(Subtask subtask);

    Subtask deleteSubtask(int idSubtask);

    void deleteAllSubtask();

    //методы для Epic
    Epic addNewgEpic(Epic epic);

    Epic updateEpic(Epic epic);

    Epic getEpic(int id);

    ArrayList<Epic> allEpics();

    Epic deleteEpicToId(int id);

    void deleteEpic();

    //История задач
    List<Task> getHistory();

    List<Task> getPrioritizedTasks ();


}
