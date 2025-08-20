package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import tasks.Epic;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class EpicHandler extends BaseHttpHandler {
    private final TaskManager taskManager;
    private final Gson gson = new Gson();

    public EpicHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case "GET":
                handleGetEpics(exchange);
                break;
            case "POST":
                handleAddEpic(exchange);
                break;
            case "DELETE":
                handleDeleteEpic(exchange);
                break;
            default:
                sendResponse(exchange, "Такого метода нет", 405);
                break;
        }
    }

    private void handleGetEpics(HttpExchange exchange) throws IOException {
        List<Epic> epics = taskManager.allEpics();
        String jsonResponse = gson.toJson(epics);
        sendResponse(exchange, jsonResponse, 200);
    }

    private void handleAddEpic(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Epic newEpic = gson.fromJson(body, Epic.class);

        try {
            taskManager.addNewgEpic(newEpic);
            sendResponse(exchange, "Эпик успешно добавлен", 201);
        } catch (IllegalArgumentException e) {
            sendConflict(exchange);
        }
    }

    private void handleDeleteEpic(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        if (query == null || !query.startsWith("id=")) {
            sendResponse(exchange, "ID задачи не указан", 400);
            return;
        }
        int id = Integer.parseInt(query.split("=")[1]);
        taskManager.deleteEpicToId(id);
        sendResponse(exchange, "Задача успешно удалена", 200);
    }
}