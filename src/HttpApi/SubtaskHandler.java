package HttpApi;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import tasks.Subtask;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SubtaskHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public SubtaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "GET":
                handleGetSubtasks(exchange);
                break;
            case "POST":
                handleAddSubtask(exchange);
                break;
            case "DELETE":
                handleDeleteSubtask(exchange);
                break;
            default:
                sendResponse(exchange, "Такого метода нет", 405);
                break;
        }
    }

    private void handleGetSubtasks(HttpExchange exchange) throws IOException {
        List<Subtask> subtasks = taskManager.allSubtasks();
        String jsonResponse = gson.toJson(subtasks);
        sendResponse(exchange, jsonResponse, 200);
    }

    private void handleAddSubtask(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Subtask newSubtask = gson.fromJson(body, Subtask.class);

        try {
            taskManager.addNewSubtask(newSubtask);
            sendResponse(exchange, "Подзадача успешно добавлена", 201);
        } catch (IllegalArgumentException e) {
            sendConflict(exchange);
        }
    }


    private void handleDeleteSubtask(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("id=")) {
            sendResponse(exchange, "ID задачи не указан", 400);
            return;
        }
        int id = Integer.parseInt(query.split("=")[1]);
        taskManager.deleteSubtask(id);
        sendResponse(exchange, "Задача успешно удалена", 200);
    }
}
