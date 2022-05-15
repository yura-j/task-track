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

    public CompressedTaskDto() {
    }

    public void setCompressionMap(Map<String, Integer> compressionMap) {
        this.compressionMap = compressionMap;
    }

    protected Map<String, Integer> compressionMap = null;

    /**
     * id,type,name,status,description,epic
     *
     * @return
     */
    @Override
    public String compress() {
        List<String> fields = Stream.of(id, type.toString(), name, status, description, epicId)
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
            if (!fields[epicIndex].isEmpty()) {
                this.epicId = Integer.parseInt(fields[epicIndex]);
            }

        } catch (Exception e) {
            throw new ParsingException("Не могу распознать задачу");
        }
    }
}
