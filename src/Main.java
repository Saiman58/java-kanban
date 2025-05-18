import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.Taskstatus;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        System.out.println();
        System.out.println("Проверка: Создание таски1");
        Task task1 = new Task("Имя", "Кефир, морковь", Taskstatus.NEW);
        taskManager.createTask(task1);
        System.out.println(task1);

        System.out.println();
        System.out.println("Проверка: Создание таски2");
        Task task2 = new Task("Имя2", "Кефир, морковь, фасоль", Taskstatus.NEW);
        taskManager.createTask(task2);
        System.out.println(task2);

        System.out.println();
        System.out.println("Проверка: Обновления таски");
        Task taskForUpdate = new Task(task1.getId(), "Новое имя", task1.getDescription() + ", молоко", Taskstatus.IN_PROGRESS);
        taskForUpdate = taskManager.updateTask(taskForUpdate);
        System.out.println(taskForUpdate);

        System.out.println();
        System.out.println("Проверка: Получение таски по id");
        System.out.println(taskManager.getTask(task1.getId()));

        System.out.println();
        System.out.println("Проверка: Удаление таски по id");
        Task deleteTask = taskManager.deleteTask(taskForUpdate.getId());
        System.out.println(deleteTask);

        System.out.println();
        System.out.println("Проверка: Вывод всех тасок");
        System.out.println(taskManager.allTasks());

        System.out.println();
        taskManager.deleteTasks();

        System.out.println();
        System.out.println("Проверка: Вывод всех тасок");
        System.out.println(taskManager.allTasks());

        System.out.println("--------------------------");

        System.out.println();
        System.out.println("Проверка: Создание Subtask1");
        Subtask subtask1 = new Subtask("Новый Subtask1", "Описание Subtask1", Taskstatus.NEW, 1);
        taskManager.addNewSubtask(subtask1);
        System.out.println(subtask1);

        System.out.println();
        System.out.println("Проверка: Создание Subtask2");
        Subtask subtask2 = new Subtask("Новый Subtask2", "Описание Subtask2", Taskstatus.NEW, 2);
        taskManager.addNewSubtask(subtask2);
        System.out.println(subtask2);

        System.out.println();
        System.out.println("Проверка: Создание Subtask3");
        Subtask subtask3 = new Subtask(" Subtask3", " описание Subtask3", Taskstatus.NEW, 1);
        taskManager.addNewSubtask(subtask3);
        System.out.println(subtask3);

        System.out.println();
        System.out.println("Проверка: Обновления Subtask");
        Subtask subtaskForUpdate = new Subtask(subtask1.getId(), 1, "Обновление подзадачи1", "Обновление описания подзадачи2", Taskstatus.IN_PROGRESS);
        subtaskForUpdate = taskManager.updateSubtask(subtaskForUpdate);
        System.out.println(subtaskForUpdate);

        System.out.println();
        System.out.println("Проверка: Получение таски по id");
        System.out.println(taskManager.getSubtasksForId(subtask1.getId()));

        System.out.println();
        System.out.println("Проверка: Вывод всех Subtask");
        System.out.println(taskManager.allSubtasks());

        System.out.println();
        System.out.println("Проверка: Удаление Subtask по id");
        Subtask deleteSubtask = taskManager.deleteSubtask(subtask2.getId());
        System.out.println(deleteSubtask);

        System.out.println();
        System.out.println("Проверка: Вывод всех Subtask");
        System.out.println(taskManager.allSubtasks());

        System.out.println();
        System.out.println("Очистка всех Subtask");
        taskManager.deleteAllSubtask();
        System.out.println(taskManager.allSubtasks());




        System.out.println("--------------------------");

        System.out.println();
        System.out.println("Проверка: Создание Epic1");
        Epic epic1 = new Epic("Отпуск", "Купить билеты");
        taskManager.creatingEpic(epic1);
        System.out.println(epic1);

        System.out.println();
        System.out.println("Проверка: Создание Epic2");
        Epic epic2 = new Epic("Обучение", "Пройти до конца курс Яндекс Практикум");
        taskManager.creatingEpic(epic2);
        System.out.println(epic2);

        System.out.println();
        System.out.println("Проверка: Обновления Epic");
        Epic updateEpic = new Epic
                (epic1.getId(), 1, "Съездить в деревню", "Взять с собой:");
        updateEpic = taskManager.updateEpic(updateEpic);
        System.out.println(updateEpic);

        System.out.println();
        System.out.println("Проверка: Создание Subtask4 в Epic");
        Subtask subtask4 = new Subtask(" Subtask4", " описание Subtask4", Taskstatus.NEW, 3);
        taskManager.addNewSubtask(subtask4);
        System.out.println(subtask4);
        System.out.println(epic1);

        System.out.println();
        System.out.println("Проверка: Получение Epic по id");
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println();
        System.out.println(taskManager.getEpic(epic2.getId()));

        System.out.println();
        System.out.println("Проверка: Вывод всех Epic");
        System.out.println(taskManager.allEpics());
        System.out.println(taskManager.allEpics());

        System.out.println();
        System.out.println("Проверка: Удаление Epic по id");
        Epic deleteEpic = taskManager.deleteEpicToId(epic1.getId());
        System.out.println(deleteEpic);

        System.out.println();
        System.out.println("Проверка: Вывод всех Epic");
        System.out.println(taskManager.allEpics());


        System.out.println();
        System.out.println("Проверка: Удаление всех Epic");
        taskManager.deleteEpic();
        System.out.println(taskManager.allEpics());

        System.out.println("--------------------------");
        System.out.println("Проверка всех задач окончена!");
        System.out.println("Спасибо за уделённое время! Продуктивной работы!");

    }
}
