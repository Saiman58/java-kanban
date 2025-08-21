package http;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;
import tasks.Epic;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;


public class EpicHandler extends BaseHttpHandler {

    public EpicHandler(TaskManager taskManager) {
        super(taskManager);
    }

    @Override //GET
    protected void processGet(HttpExchange exchange) throws IOException {
        List<Epic> epics = taskManager.allEpics();
        String jsonResponse = gson.toJson(taskManager.allEpics());
        sendResponse(exchange, jsonResponse, 200);
    }

    @Override // POST
    protected void processPost(HttpExchange exchange) throws IOException {
        // Читаем тело запроса с помощью BufferedReader
        String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));

        Epic newEpic = gson.fromJson(body, Epic.class);

        try {
            taskManager.addNewgEpic(newEpic); // Добавляем новый эпик
            sendResponse(exchange, "Эпик успешно добавлен", 201);
        } catch (IllegalArgumentException e) {
            sendConflict(exchange); // Обрабатываем конфликт
        }
    }

    @Override
    protected void processDelete(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery(); // Получаем параметры запроса
        if (query == null || !query.startsWith("id=")) {
            sendResponse(exchange, "ID эпика не указан", 400); // Отправляем ответ с кодом 400
            return;
        }
        int id = Integer.parseInt(query.split("=")[1]); // Извлекаем ID эпика
        taskManager.deleteEpicToId(id); // Удаляем эпик
        sendResponse(exchange, "Эпик успешно удален", 200); // Отправляем ответ с кодом 200
    }
}



