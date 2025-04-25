package service;

import model.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.io.*;
import java.util.*;


public class FileServiceTest {

    @BeforeEach
    void resetVault() {
        Vault.users.clear();
    }
    
    // File checking
    @Test
    void check_if_file_exists() {
        FileService.loadFromFile();
        assertTrue(Vault.users.size() > 0);
    }

    // File loading
    @Test
    void init_file() {
        FileService.DB_PATH = Paths.get("src/test/resources/test-users.json");
        FileService.loadFromFile();
        assertTrue(Vault.users.size() > 0);
    }

    // Test file loading exception
    @Test 
    void init_invalid_file() {
        FileService.testMode = true;

        FileService.DB_PATH = Paths.get("src/test/resources/test-invalid.json");
        assertThrows(RuntimeException.class, () -> FileService.loadFromFile());
    }

    // Saving file
    @Test
    void test_save() throws IOException {
        FileService.testMode = true;
        FileService.DB_PATH = Paths.get("src/test/resources/out-users.json");

        Vault.users.add(new User("Test", "test@dev.com", 15));

        FileService.saveToFileAndExit();

        // Verify file exist and it's content
        List<String> lines = Files.readAllLines(FileService.DB_PATH);
        assertTrue(lines.size() > 0);
        assertTrue(lines.get(0).contains("\"name\":\"Test\""));
    }

    // Saving invalid file
    @Test
    void save_invalid_file() throws IOException {
        FileService.testMode = true;

        // Invalid path
        FileService.DB_PATH = Paths.get("src/test/resoruces/invalid-path.json");

        assertThrows(RuntimeException.class, () -> FileService.saveToFileAndExit());
    }
}