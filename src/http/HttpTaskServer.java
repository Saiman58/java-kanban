package http;

import com.sun.net.httpserver.HttpServer; //импорт
import manager.Managers;
import manager.TaskManager;
import tasks.Task;
import tasks.Taskstatus;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private final TaskManager taskManager;

    public HttpTaskServer(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0); // сервер слушающий порт 8080

        //привязка обработчиков к путям
        server.createContext("/tasks", new TaskHandler(taskManager));
        server.createContext("/subtasks", new SubtaskHandler(taskManager));
        server.createContext("/epics", new EpicHandler(taskManager));
        server.createContext("/history", new HistoryHandler(taskManager));
        server.createContext("/prioritized", new PrioritizedHandler(taskManager));

        server.start();
    }

    public static void main(String[] args) throws IOException {
        TaskManager taskManager = Managers.getDefault();

        HttpTaskServer server = new HttpTaskServer(taskManager); //сервер с менеджером задач
        server.start(); //запуск сервера
        System.out.println("Сервер работает по порту 8080");
        System.out.println("Проверка: Создание Task1");
        Task task1 = new Task("Task1", "Кефир, морковь", Taskstatus.NEW, LocalDateTime.now(), Duration.ZERO);
    }
}
