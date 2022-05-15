package ru.yandex.practicum.util;

public interface Compressible {

    String compress();

    void decompress(String compressedData);
}
