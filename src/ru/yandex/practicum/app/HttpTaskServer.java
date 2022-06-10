package ru.yandex.practicum.app;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    public static final int PORT = 8080;
    HttpServer server;

    public HttpTaskServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        new Routes(server);
    }

    public void start() {
        this.server.start();
    }

    public void stop() {
        this.server.stop(0);
    }
}
