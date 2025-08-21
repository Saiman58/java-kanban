package http;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import tasks.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class TaskHandler extends BaseHttpHandler {

    public TaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override //GET
    protected void processGet(HttpExchange exchange) throws IOException {
        String jsonResponse = gson.toJson(taskManager.allTasks());
        sendResponse(exchange, jsonResponse, 200);
    }

    @Override // POST
    protected void processPost(HttpExchange exchange) throws IOException {
        String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        Task newTask = gson.fromJson(body, Task.class);

        try {
            taskManager.addNewTask(newTask);
            sendResponse(exchange, "Задача успешно добавлена", 201);
        } catch (IllegalArgumentException e) {
            sendConflict(exchange);
        }
    }


    @Override //DELETE
    protected void processDelete(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("id=")) {
            sendResponse(exchange, "ID задачи не указан", 400);
            return;
        }
        int id = Integer.parseInt(query.split("=")[1]);
        taskManager.deleteTask(id); // Удаляем задачу
        sendResponse(exchange, "Задача успешно удалена", 200);
    }

}
