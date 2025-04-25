package service;

import model.User;
import util.ConsoleHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.*;
import java.util.*;

public class UserServiceTest {
    
    // User addition
    @Test
    void test_valid_user_data() {

        String simulatedInput = "TestGeorge\ngorgo@mutta.fk\n66\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());

        UserService.scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));
        ConsoleHelper.scanner = new Scanner(new ByteArrayInputStream(simulatedInput.getBytes()));

        Vault.users.clear();

        UserService.registerUser();

        assertEquals(1, Vault.users.size());
        assertEquals("TestGeorge", Vault.users.get(0).name);

    }

    @Test 
    void test_invalid_user_data() {
        String simulatedWrongInput = "T\nmuk\nQ\n";

        UserService.scanner = new Scanner(new ByteArrayInputStream(simulatedWrongInput.getBytes()));
        ConsoleHelper.scanner = new Scanner(new ByteArrayInputStream(simulatedWrongInput.getBytes()));

        Vault.users.clear();

        assertThrows(RuntimeException.class, () -> UserService.registerUser());
    }

    // Searching User
    @Test
    void searching_user() {
        Vault.users.clear();
        Vault.users.add(new User("TestGeorge", "gorgo@mutta.fk", 66));
        
        String simulatedSearchInput = "TestGeorge";
        InputStream inputStream = new ByteArrayInputStream(simulatedSearchInput.getBytes());

        UserService.scanner = new Scanner(new ByteArrayInputStream(simulatedSearchInput.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        UserService.searchUser();

        System.setOut(originalOut);
        String printed = output.toString();

        assertTrue(printed.contains("TestGeorge (66) gorgo@mutta.fk"));

    }

    @Test
    void invalid_searching_user() {
        Vault.users.clear();
        Vault.users.add(new User("TestGeorge", "gorgo@mutta.fk", 66));
        
        String simulatedSearchInput = "TestPabito";
        InputStream inputStream = new ByteArrayInputStream(simulatedSearchInput.getBytes());

        UserService.scanner = new Scanner(new ByteArrayInputStream(simulatedSearchInput.getBytes()));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        


        System.setOut(new PrintStream(output));
        UserService.searchUser();
        System.setOut(originalOut);
        String printed = output.toString();
        
        assertTrue(printed.contains("No match"));

    }

}