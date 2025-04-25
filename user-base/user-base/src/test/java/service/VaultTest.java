package service;

import model.User;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VaultTest {

    @Test
    void test_user_presistence_across_services() {
        Vault.users.clear();

        Vault.users.add(new User("Timmy", "Te@mo.com", 44));

        boolean found = false;

        for(User u : Vault.users){
            if (u.name.equals("Timmy")) {
                found = true;
                break;
            }
        }

        assertTrue(found);
    }


    @Test
    void test_no_replicace_users_on_reload() {
        Vault.users.clear();
        FileService.DB_PATH = Paths.get("src/test/resources/test-users.json");
        FileService.testMode = true;

        // First load
        FileService.loadFromFile();
        int firstLoadCount = Vault.users.size();

        // Second load
        FileService.loadFromFile();
        int secondLoadCount = Vault.users.size();

        assertEquals(firstLoadCount, secondLoadCount);

    }
}