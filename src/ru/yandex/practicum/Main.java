package ru.yandex.practicum;

import ru.yandex.practicum.kv_server.KVServer;
import ru.yandex.practicum.app.Application;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/*
* Пояснительная записка.
* Привет.
* KVServer имеет захардкоженную булку дебаг, которая включает или отключает метод, который отдает все содержимое KVS
*       для того чтобы просто из браузера можно было достучаться и посмотреть содержимое данных.
* Маппинг хранится в файле Routes. Это сделано для того чтобы ослабить зависимость между кодом сервера и маппингом.
* Логика приема запроса и формирования ответа сконцетрирована в файле AbstractController, его наследники будут
*       роутить эндпоинты
* Для некоторых контроллеров написаны юзкейсы, которые хранятся в папке use_cases
* Для сериализации/десериализации написана обертка над gson, хранится в классе Json, а также сериализатор  и
*       десериализатор объекта задачи TaskDto.
* Для того, чтобы не писать новые тесты для тестирования эндпоинтов был написан адаптер RemoteClientTaskManager,
*       который реализует интерфейс TaskManager. Преимущество подхода в том, что можно сделать
*       RemoteClientTaskManagerTest и занаследовать базовые тестовые методы от TaskManagerTest, что является неплохим
*       дополнением для тестирования эндпоинтов.
* Спасибо за внимание !!!
*/


public class Main {

    public static void main(String[] args) throws IOException {
        KVServer kv = new KVServer();
        kv.start();
        Application app = new Application();
        app.start();


        System.out.println("Нажмите любую строчку, чтобы остановить программу");
        Scanner scanner = new Scanner(System.in);
        String stopSignal = scanner.nextLine();
        System.out.println("Завершаю работу");


        app.stop();
        kv.stop();
    }
}
