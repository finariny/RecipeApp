package com.example.recipeapp.service;

import java.nio.file.Path;

public interface FileService {

    void saveToFile(Path url, String json);

    String readFromFile(Path url);

    void cleanFile(Path url);
}
