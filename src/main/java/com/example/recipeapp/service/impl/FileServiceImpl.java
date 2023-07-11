package com.example.recipeapp.service.impl;

import com.example.recipeapp.service.FileService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public void saveToFile(Path url, String json) {
        try {
            cleanFile(url);
            Files.writeString(url, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readFromFile(Path url) {
        try {
            return Files.readString(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanFile(Path url) {
        try {
            Files.deleteIfExists(url);
            Files.createFile(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
