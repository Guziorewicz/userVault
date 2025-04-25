package service;

import model.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import util.ConsoleHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.*;
import java.util.*;

public class AdminServiceTest {

    // Reset Vault
    @BeforeEach
    void resetVault() {
        Vault.users.clear();
    }
    
    @AfterEach
    void resetMode() {
        AdminService.testMode = false;
    }

    // Correct password redirection
    @Test
    void test_valid_passwd() {

        AdminService.testMode = true;

        AdminService.setAdminPasswd("testpasswd");

        String simulatedPasswd = "testpasswd\n";

        AdminService.scanner = new Scanner(new ByteArrayInputStream(simulatedPasswd.getBytes()));


        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));
        
        AdminService.registerAdmin();

        System.setOut(originalOut);
        String printed = output.toString();

        System.out.println(printed);
        assertTrue(printed.contains("Granted access"));
    }

    // Invalid password redirection
    @Test
    void test_invalid_passwd() {
        AdminService.testMode = true;
        
        AdminService.setAdminPasswd("testpasswd");
        String simulatedPasswd = "invalidpasswd\n";
        
        AdminService.scanner = new Scanner(new ByteArrayInputStream(simulatedPasswd.getBytes()));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));
        
        AdminService.registerAdmin();
        
        System.setOut(originalOut);
        String printed = output.toString();
        
        System.out.println(printed);
        assertTrue(printed.contains("Invalid Password"));
    }

    // Test user edition
    @Test
    void test_edit_user() {

        FileService.DB_PATH = Paths.get("src/test/resources/test-users.json");
        FileService.testMode = true;

        PrintStream orginalOutput = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));

        // Get Mock data
        FileService.loadFromFile();
        System.setOut(orginalOutput);

        // Set id 
        String userInput = """
        1
        Tony
        ton@y.com
        27
        """;

        // Initiate scanner
        Scanner inputScanner = new Scanner(new ByteArrayInputStream(userInput.getBytes()));
        AdminService.scanner = inputScanner;
        ConsoleHelper.scanner = inputScanner;

        // Fetch output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        // Run function

        try {
            AdminService.userEdition();
            } catch (NoSuchElementException e) {
        System.out.print("Something's wrong" + e);}

        System.setOut(orginalOutput);

        // Get output
        String printed = output.toString();

        assertTrue(printed.contains("Appended new user data."));


    }

    // Delete user
    @Test 
    void test_delete_user() {
        FileService.DB_PATH = Paths.get("src/test/resources/test-users.json");
        FileService.testMode = true;

        PrintStream orginalOutput = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));

        // Get Mock data
        FileService.loadFromFile();
        System.setOut(orginalOutput);

        String userInput = "1\ny\n";

        // Initiate scanner
        Scanner inputScanner = new Scanner(new ByteArrayInputStream(userInput.getBytes()));
        AdminService.scanner = inputScanner;
        ConsoleHelper.scanner = inputScanner;

        // Fetch output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        // Run function
        AdminService.userDeletion();

        // Check that the user was deleted 
        String printed = output.toString();
        String result = Vault.users.toString();

        System.out.print(printed);
        System.out.print(result);

        assertTrue(result.contains("Bob"));
        assertFalse(result.contains("Alice"));
        
    }

    // Non existent data
    @Test 
    void test_edit_wrong_user() {
        FileService.DB_PATH = Paths.get("src/test/resources/test-users.json");
        FileService.testMode = true;

        PrintStream orginalOutput = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));

        // Get Mock data
        FileService.loadFromFile();
        System.setOut(orginalOutput);

        String userInput = "99";

        // Initiate scanner
        Scanner inputScanner = new Scanner(new ByteArrayInputStream(userInput.getBytes()));
        AdminService.scanner = inputScanner;
        ConsoleHelper.scanner = inputScanner;

        // Fetch output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        // Run function
        AdminService.userEdition();
        

        System.setOut(orginalOutput);

        // Get output
        String printed = output.toString();

        assertTrue(printed.contains("No match found"));


    }

    @Test 
    void test_delete_wrong_user() {
        FileService.DB_PATH = Paths.get("src/test/resources/test-users.json");
        FileService.testMode = true;

        PrintStream orginalOutput = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));

        // Get Mock data
        FileService.loadFromFile();
        System.setOut(orginalOutput);

        String userInput = "99";

        // Initiate scanner
        Scanner inputScanner = new Scanner(new ByteArrayInputStream(userInput.getBytes()));
        AdminService.scanner = inputScanner;
        ConsoleHelper.scanner = inputScanner;

        // Fetch output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        // Run function
        AdminService.userDeletion();
        

        System.setOut(orginalOutput);

        // Get output
        String printed = output.toString();

        assertTrue(printed.contains("No match found"));


    }

    
}