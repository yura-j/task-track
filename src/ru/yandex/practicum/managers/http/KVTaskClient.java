package ru.yandex.practicum.managers.http;

import ru.yandex.practicum.exceptions.HttpManagerException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String host;
    private HttpClient client = HttpClient.newHttpClient();
    private HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
    private String token;

    public KVTaskClient(String host) {
        this.host = host;
        register();
    }

    public void put(String key, String json) {
        URI uri = URI.create(host + "/save/" + key + "?API_TOKEN=" + token);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "text/html")
                .build();
        HttpResponse<String> response = this.execMethod(request);
        if (null == response || 200 != response.statusCode()) {
            throw new HttpManagerException("Не получилось подключить KVServer");
        }
    }

    public String load(String key) {
        URI uri = URI.create(host + "/load/" + key + "?API_TOKEN=" + token);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "text/html")
                .build();
        HttpResponse<String> response = this.execMethod(request);
        if (null != response && 200 == response.statusCode()) {
            return response.body();
        } else if (null != response && 400 == response.statusCode()) {
            return "";
        } else {
            throw new HttpManagerException("Не получилось подключить KVServer");
        }
    }

    private void register() {
        URI uri = URI.create(host + "/register");
        System.out.println(uri);
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "text/html")
                .build();
        HttpResponse<String> response = this.execMethod(request);
        if (null != response && 200 == response.statusCode()) {
            token = response.body();
        } else {
            throw new HttpManagerException("Не получилось подключить KVServer");
        }
    }

    private HttpResponse<String> execMethod(HttpRequest request) {
        HttpResponse<String> response = null;
        try {
            response = client.send(request, handler);
        } catch (Exception e) {
            System.out.println("Произошла ошибка");
            e.printStackTrace();
        }
        return response;
    }
}
