package com.example.recipeapp.service;

import java.nio.file.Path;

/**
 * Сервис для работы с файлами
 */
public interface FileService {

    /**
     * Сохраняет в файл
     *
     * @param url  URL файла
     * @param json строка в формате JSON
     */
    void saveToFile(Path url, String json);

    /**
     * Читает из файла
     *
     * @param url URL файла
     * @return строка, содержащая контент файла
     */
    String readFromFile(Path url);

    /**
     * Очищает содержимое файла
     *
     * @param url URL файла
     */
    void cleanFile(Path url);
}
