package http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public abstract class BaseHttpHandler implements HttpHandler {
    protected final Gson gson;
    protected final TaskManager taskManager;

    // Конструктор класса, принимающий экземпляр TaskManager
    public BaseHttpHandler(TaskManager taskManager) {
        this.gson = new Gson();
        this.taskManager = taskManager;
    }


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        switch (method) {
            case "GET":
                processGet(exchange);
                break;
            case "POST":
                processPost(exchange);
                break;
            case "DELETE":
                processDelete(exchange);
                break;
            default:
                writeToUser(exchange, "Данный метод не предусмотрен");
                break;
        }
    }

    protected void processGet(HttpExchange exchange) throws IOException {
        writeToUser(exchange, "Метод GET не реализован");
    }

    protected void processPost(HttpExchange exchange) throws IOException {
        writeToUser(exchange, "Метод POST не реализован");
    }

    protected void processDelete(HttpExchange exchange) throws IOException {
        writeToUser(exchange, "Метод DELETE не реализован");
    }

    protected void writeToUser(HttpExchange exchange, String message) throws IOException {
        exchange.sendResponseHeaders(405, message.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(message.getBytes(StandardCharsets.UTF_8));
        }
    }

    protected void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    protected void sendConflict(HttpExchange exchange) throws IOException {
        sendResponse(exchange, "Конфликт при добавлении задачи", 409);
    }

}
