package ru.yandex.practicum.app;

public class HttpServerResponse {
    public String message;
    public AbstractController.ResponseStatus status;
    public Object payload;

    public HttpServerResponse(String message, AbstractController.ResponseStatus status, Object payload) {
        this.message = message;
        this.status = status;
        this.payload = payload;
    }
}
