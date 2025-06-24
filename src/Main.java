import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.Taskstatus;

public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = Managers.getDefault();

        System.out.println();
        System.out.println("Проверка: Создание Task1");
        Task task1 = new Task("Task1", "Кефир, морковь", Taskstatus.NEW);
        taskManager.createTask(task1);
        System.out.println(task1);

        System.out.println();
        System.out.println("Проверка: Создание Task2");
        Task task2 = new Task("Task2", "Кефир, морковь, фасоль", Taskstatus.NEW);
        taskManager.createTask(task2);
        System.out.println(task2);

        System.out.println();
        System.out.println("Проверка: Создание Task3");
        Task task3 = new Task("Task3", "Описание Task3", Taskstatus.NEW);
        taskManager.createTask(task3);
        System.out.println(task3);

        System.out.println();
        System.out.println("Проверка: Создание Task4");
        Task task4 = new Task("Task4", "Описание Task4", Taskstatus.NEW);
        taskManager.createTask(task4);
        System.out.println(task4);

        System.out.println();
        System.out.println("Проверка: Создание Task5");
        Task task5 = new Task("Task5", "Описание Task5", Taskstatus.NEW);
        taskManager.createTask(task5);
        System.out.println(task5);

        System.out.println();
        System.out.println("Проверка: Обновления Task");
        Task taskForUpdate = new Task(task1.getId(), "Новое Task1.1", task1.getDescription() + ", молоко, хлеб", Taskstatus.IN_PROGRESS);
        taskForUpdate = taskManager.updateTask(taskForUpdate);
        System.out.println(taskForUpdate);

        System.out.println();
        System.out.println("Проверка: Просмотр Task1 по id");
        System.out.println(taskManager.getTask(task1.getId()));

        System.out.println();
        System.out.println("Проверка: Просмотр Task5 по id");
        System.out.println(taskManager.getTask(task5.getId()));

        System.out.println("--------------------------");
        System.out.println("Проверка: вывода истории просмотров");
        System.out.println(taskManager.getHistory());
        System.out.println();

        System.out.println();
        System.out.println("Проверка: Просмотр Task3 по id");
        System.out.println(taskManager.getTask(task3.getId()));

        System.out.println();
        System.out.println("Проверка: Просмотр Task4 по id");
        System.out.println(taskManager.getTask(task4.getId()));

        System.out.println();
        System.out.println("Проверка: Просмотр Task5 по id");
        System.out.println(taskManager.getTask(task5.getId()));

        System.out.println();
        System.out.println("Проверка: Удаление Task по id");
        Task deleteTask = taskManager.deleteTask(taskForUpdate.getId());
        System.out.println(deleteTask);

        System.out.println();
        System.out.println("Проверка: Вывод всех Task");
        System.out.println(taskManager.allTasks());

        System.out.println();
        taskManager.deleteTasks();

        System.out.println();
        System.out.println("Проверка: Вывод всех Task");
        System.out.println(taskManager.allTasks());

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
        System.out.println("Проверка: Обновления Epic1");
        Epic updateEpic = new Epic(epic1.getId(), 1, "Съездить в деревню", "Взять с собой:");
        updateEpic = taskManager.updateEpic(updateEpic);
        System.out.println(updateEpic);

        System.out.println();
        System.out.println("Проверка: Создание Epic3");
        Epic epic3 = new Epic("Имя", "Описание");
        taskManager.creatingEpic(epic3);
        System.out.println(epic3);

        System.out.println();
        System.out.println("Проверка: Создание Subtask1 в Epic");
        Subtask subtask1 = new Subtask(" Subtask1", " описание Subtask1", Taskstatus.NEW, 8);
        taskManager.addNewSubtask(subtask1);
        System.out.println(subtask1);
        System.out.println(epic1);

        System.out.println();
        System.out.println("Проверка: Просмотр Epic по id");
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

        System.out.println();
        System.out.println("Проверка: Создание Epic4");
        Epic epic4 = new Epic("Имя", "Описание");
        taskManager.creatingEpic(epic4);
        System.out.println(epic4);

        System.out.println();
        System.out.println("Проверка: Создание Subtask2");
        Subtask subtask2 = new Subtask("Новый Subtask2", "Описание Subtask1", Taskstatus.NEW, 10);
        taskManager.addNewSubtask(subtask2);
        System.out.println(subtask2);

        System.out.println();
        System.out.println("Проверка: Создание Epic5");
        Epic epic5 = new Epic("Имя", "Описание");
        taskManager.creatingEpic(epic5);
        System.out.println(epic5);

        System.out.println();
        System.out.println("Проверка: Создание Subtask3");
        Subtask subtask3 = new Subtask("Новый Subtask3", "Описание Subtask3", Taskstatus.NEW, 12);
        taskManager.addNewSubtask(subtask3);
        System.out.println(subtask3);

        System.out.println();
        System.out.println("Проверка: Обновления Subtask1");
        Subtask subtaskForUpdate = new Subtask(subtask1.getId(), 1, "Обновление подзадачи1", "Обновление описания подзадачи2", Taskstatus.IN_PROGRESS);
        subtaskForUpdate = taskManager.updateSubtask(subtaskForUpdate);
        System.out.println(subtaskForUpdate);

        System.out.println();
        System.out.println("Проверка: Просмотр Subtask по id");
        System.out.println(taskManager.getSubtasksForId(subtask2.getId()));

        System.out.println();
        System.out.println("Проверка: Просмотр всех Subtask");
        System.out.println(taskManager.allSubtasks());

        System.out.println();
        System.out.println("Проверка: Удаление Subtask по id");
        Subtask deleteSubtask = taskManager.deleteSubtask(subtask2.getId());
        System.out.println(deleteSubtask);

        System.out.println();
        System.out.println("Проверка: Просмотр всех Subtask");
        System.out.println(taskManager.allSubtasks());

        System.out.println();
        System.out.println("Очистка всех Subtask");
        taskManager.deleteAllSubtask();
        System.out.println(taskManager.allSubtasks());

        System.out.println("--------------------------");
        System.out.println("Проверка всех задач окончена!");
        System.out.println("Спасибо за уделённое время! Продуктивной работы!");

        System.out.println("--------------------------");
        System.out.println("Проверка: вывода истории просмотров");
        System.out.println(taskManager.getHistory());
        System.out.println();

    }

}
