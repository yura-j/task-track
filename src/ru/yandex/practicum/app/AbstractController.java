package ru.yandex.practicum.app;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.yandex.practicum.exceptions.ControllerException;
import ru.yandex.practicum.util.Json;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

abstract public class AbstractController implements HttpHandler {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";


    protected HashMap<String, String> queryArguments;
    protected String pathArgument;
    protected HttpExchange exchange;
    protected HttpServerResponse response;

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        HttpContext context = exchange.getHttpContext();
        this.exchange = exchange;
        pathArgument = uri.getPath().substring(context.getPath().length());
        parseQueryParams(uri.getRawQuery());
        response = new HttpServerResponse("", ResponseStatus.undefined, List.of());
        try {
            switch (method) {
                case GET:
                    get();
                    response.status = ResponseStatus.success;
                    break;
                case POST:
                    post();
                    response.status = ResponseStatus.success;
                    break;
                case DELETE:
                    delete();
                    response.status = ResponseStatus.success;
                    break;
                default:
                    throw new ControllerException("Запрос не может быть обработан");
            }
        } catch (Exception e) {
            String message = e.getMessage();
            if (null == message) {
                message = "";
            }
            response.status = ResponseStatus.failed;
            response.message = message;
            response.payload = List.of();
        }
        sendOk();
    }

    private void sendOk() throws IOException {
        String responseJson = Json.build().to(response);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, responseJson.getBytes(UTF_8).length);
        exchange.getResponseBody().write(responseJson.getBytes(UTF_8));
    }

    protected abstract void get();

    protected abstract void post();

    protected abstract void delete();


    protected String getBody() {
        try {
            return new String(exchange.getRequestBody().readAllBytes(), UTF_8);
        } catch (Exception e) {
            throw new ControllerException("Не удается получить тело запроса");
        }
    }

    private void parseQueryParams(String query) {
        queryArguments = new HashMap<>();
        if (null != query) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] entry = pair.split("=");
                if (entry.length != 2) {
                    continue;
                }
                String rawKey = entry[0];
                String rawValue = entry[1];

                queryArguments.put(
                        URLDecoder.decode(rawKey, UTF_8),
                        URLDecoder.decode(rawValue, UTF_8)
                );
            }
        }
    }

    protected void exception(String message) {

    }

    protected static class Response {
        public String message;
        public ResponseStatus status;
        public Object payload;

        public Response(String message, ResponseStatus status, Object payload) {
            this.message = message;
            this.status = status;
            this.payload = payload;
        }
    }

    public enum ResponseStatus {
        undefined,
        success,
        failed
    }
}
