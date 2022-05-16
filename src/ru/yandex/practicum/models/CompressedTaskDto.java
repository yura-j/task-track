package ru.yandex.practicum.models;

import ru.yandex.practicum.exceptions.ParsingException;
import ru.yandex.practicum.util.Compressible;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompressedTaskDto implements Compressible {
    public int id;
    public TaskType type;
    public String name;
    public TaskStatus status;
    public String description;
    public int epicId;

    public void setCompressionMap(Map<String, Integer> compressionMap) {
        this.compressionMap = compressionMap;
    }

    protected Map<String, Integer> compressionMap = null;

    public String exportEpicIdToString() {
        return this.epicId == 0 ? "" : String.valueOf(epicId);
    }
    /**
     * id,type,name,status,description,epic
     *
     * @return
     */
    @Override
    public String compress() {
        List<String> fields = Stream.of(id, type.toString(), name, status, description, exportEpicIdToString())
                .map(String::valueOf)
                .collect(Collectors.toList());
        return String.join(",", fields);
    }

    /**
     * id,type,name,status,description,epic
     */
    @Override
    public void decompress(String compressedData) {
        try {
            String[] fields = compressedData.split(",", -1);
            int idIndex = this.compressionMap.get("id");
            int typeIndex = this.compressionMap.get("type");
            int nameIndex = this.compressionMap.get("name");
            int statusIndex = this.compressionMap.get("status");
            int descriptionIndex = this.compressionMap.get("description");
            int epicIndex = this.compressionMap.get("epic");

            this.id = Integer.parseInt(fields[idIndex]);
            this.type = TaskType.valueOf(fields[typeIndex]);
            this.name = fields[nameIndex].trim();
            this.status = TaskStatus.valueOf(fields[statusIndex]);
            this.description = fields[descriptionIndex].trim();
            this.epicId = 0;
            //Ошибки не будет, нули проставлять не прийдется, так как если строка пустая, то 60 строчка приведет epicId
            //в нуль, если не пустая то 67 строчка попробует проставить в epicId число. Тем не менее, я согласен, что
            //формат файла нужно соблюсти в точности. Поэтому я поправил сохранение в файл, теперь оно преобразует
            //epicId если он нуль и отправит на сохранение пустую строку. Формат пределал не спецом,
            //сначал не хотел на это отвлекаться, потом забыл поправить. Спасибо что обратиливнимание.
            if (!fields[epicIndex].isEmpty()) {
                this.epicId = Integer.parseInt(fields[epicIndex]);
            }

        } catch (Exception e) {
            throw new ParsingException("Не могу распознать задачу");
        }
    }
}
