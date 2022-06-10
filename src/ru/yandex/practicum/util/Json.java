package ru.yandex.practicum.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.yandex.practicum.models.TaskDto;
import ru.yandex.practicum.models.TaskDtoDeserializer;
import ru.yandex.practicum.models.TaskDtoSerializer;

import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.HashMap;

public class Json {
    private static class Adapter {
        Object serializer;
        Object deserializer;

        public Adapter(Object serializer, Object deserializer) {
            this.serializer = serializer;
            this.deserializer = deserializer;
        }
    }
    private Gson delegate = new Gson();
    private static HashMap<Type, Adapter> adapters = new HashMap<>();

    private Json() {
        registerAdapter(TaskDto.class, new TaskDtoSerializer(), new TaskDtoDeserializer());
        init();
    }

    public static Json build() {
        return new Json();
    }

    private void init () {
        GsonBuilder builder = new GsonBuilder();
        adapters.forEach((t, adapter) -> {
            if (null != adapter.serializer) {
                builder.registerTypeAdapter(t, adapter.serializer) ;
            }
            if (null != adapter.deserializer) {
                builder.registerTypeAdapter(t, adapter.deserializer) ;
            }
        });
        delegate = builder.create();
    }

    public static void registerAdapter(Type type, Object serializer, Object deserializer) {
        adapters.put(type, new Adapter(serializer, deserializer));
    }

    public String to(Object target) {
        init();
        return delegate.toJson(target);
    }

    public <T> T from(String jsonEncodedString, Type typeOfT) {
        init();
        return delegate.fromJson(jsonEncodedString, typeOfT);
    }
}
