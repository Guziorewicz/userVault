package service;

import java.nio.file.Path;
import java.nio.file.Paths;
import service.Vault;
import model.User;
import java.io.*;
import java.util.*;

public class FileService {
	public static boolean testMode = false;

	static Path DB_PATH = Paths.get(System.getProperty("user.dir"), "users.json");

    // File operations
	public static List<User> loadFromFile() {
        Vault.users = new ArrayList<>();

		File file = DB_PATH.toFile();
		if (!file.exists()) return Vault.users;

		if (file == null) {
		    System.out.println("users.json not found in resources.");
		    return Vault.users;
		}

		try (Scanner fileScanner = new Scanner(file)) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				Vault.users.add(User.fromJSON(line));
			}
			System.out.println("Loaded users from file.");
		} catch (Exception e) {
			System.out.println("Error loading file.");
			if (testMode) throw new RuntimeException(e);
		}
        return Vault.users;
	}

	public static void saveToFileAndExit () {
		try (PrintWriter writer = new PrintWriter(DB_PATH.toFile())) {
			for(User u : Vault.users) {
				writer.println(u.toJSON());
			} 
			System.out.println("Users saved to " + DB_PATH.getFileName());
		} catch (IOException e) {
			System.out.println("Error saving file " + e.getMessage());
			if (testMode) throw new RuntimeException(e);
		}
		System.out.println("See ya !");
		if (!testMode) System.exit(0);
	}

}