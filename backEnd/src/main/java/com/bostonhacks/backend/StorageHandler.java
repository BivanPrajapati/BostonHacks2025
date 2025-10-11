package com.bostonhacks.backend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StorageHandler {
    private static StorageHandler instance;
    private final Path storageDirectory;

    private StorageHandler() throws IOException {
        String tmpDir = System.getProperty("java.io.tmpdir");
        this.storageDirectory = Paths.get(tmpDir, "bostonhacks-uploads");

        if (!Files.exists(storageDirectory)) {
            Files.createDirectories(storageDirectory);
        }
    }

    public static synchronized StorageHandler getInstance() throws IOException {
        if (instance == null) {
            instance = new StorageHandler();
        }
        return instance;
    }

    public Path getStorageDirectory() {
        return storageDirectory;
    }

    public Path storeFile(String filename, byte[] content) throws IOException {
        Path filePath = storageDirectory.resolve(filename);
        Files.write(filePath, content);
        return filePath;
    }

    public void deleteFile(String filename) throws IOException {
        Path filePath = storageDirectory.resolve(filename);
        Files.deleteIfExists(filePath);
    }
}
