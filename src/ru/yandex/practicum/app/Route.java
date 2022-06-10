package ru.yandex.practicum.app;

import com.sun.net.httpserver.HttpServer;

import java.util.function.Consumer;

public class Route {
    StringBuilder path;
    HttpServer server;

    private Route(StringBuilder path, HttpServer server) {
        this.path = path;
        this.server = server;
    }

    public static Route of(HttpServer server, String path) {
        return new Route(new StringBuilder(path), server);
    }

    public Route add(String pathAddition, AbstractController controller) {
        String fullPath = path + pathAddition;
        server.createContext(fullPath, controller);
        System.out.println("endpoint: " + fullPath);
        System.out.println("    " + "controller: " + controller.getClass().getSimpleName());
        return this;
    }

    public Route group(String group, Consumer<Route> consumer) {
        String fullPath = path + group;
        consumer.accept(Route.of(server, fullPath));
        return this;
    }

    public void terminate() {
        //Метод ничего не делает, кроме того что пишется в конце монады, чтобы визуально видно было, где конец.
    }
}
