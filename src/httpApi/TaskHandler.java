package httpApi;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class TaskHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "GET":
                handleGetTasks(exchange);
                break;
            case "POST":
                handleAddTask(exchange);
                break;
            case "DELETE":
                handleDeleteTask(exchange);
                break;
            default:
                sendResponse(exchange, "Такого метода нет", 405);
                break;
        }
    }

    private void handleGetTasks(HttpExchange exchange) throws IOException {
        String jsonResponse = gson.toJson(taskManager.allTasks());
        sendResponse(exchange, jsonResponse, 200);
    }

    private void handleAddTask(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Task newTask = gson.fromJson(body, Task.class);

        try {
            taskManager.addNewTask(newTask);
            sendResponse(exchange, "Задача успешно добавлена", 201);
        } catch (IllegalArgumentException e) {
            sendConflict(exchange);
        }
    }

    private void handleDeleteTask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("id=")) {
            sendResponse(exchange, "ID задачи не указан", 400);
            return;
        }
        int id = Integer.parseInt(query.split("=")[1]);
        taskManager.deleteTask(id);
        sendResponse(exchange, "Задача успешно удалена", 200);
    }
}
