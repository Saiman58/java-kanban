package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import tasks.Subtask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class SubtaskHandler extends BaseHttpHandler {

    public SubtaskHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override
    protected void processGet(HttpExchange exchange) throws IOException {
        List<Subtask> subtasks = taskManager.allSubtasks();
        String jsonResponse = gson.toJson(subtasks);
        sendResponse(exchange, jsonResponse, 200);
    }

    @Override
    protected void processPost(HttpExchange exchange) throws IOException {
        String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        Subtask newSubtask = gson.fromJson(body, Subtask.class);

        try {
            taskManager.addNewSubtask(newSubtask);
            sendResponse(exchange, "Подзадача успешно добавлена", 201);
        } catch (IllegalArgumentException e) {
            sendConflict(exchange);
        }
    }

    @Override
    protected void processDelete(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("id=")) {
            sendResponse(exchange, "ID подзадачи не указан", 400);
            return;
        }
        int id = Integer.parseInt(query.split("=")[1]);
        taskManager.deleteSubtask(id);
        sendResponse(exchange, "Подзадача успешно удалена", 200);
    }
}
