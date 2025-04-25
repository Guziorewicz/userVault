package service;

import service.UserService;
import service.Vault;
import model.User;
import util.InputSanitizer;
import util.ValidationException;
import util.ConsoleHelper;
import java.io.*;
import java.io.FileReader;
import java.util.*;
import java.util.Properties;
import java.io.IOException;

public class AdminService {
	public static boolean testMode = false;


    // Admin panel
	private static String adminPasswd;

	static {
	    Properties props = new Properties();
	    try {
	        props.load(new FileReader("./.env"));
	        adminPasswd = props.getProperty("ADMIN_PASSWORD");
	    } catch (IOException e) {
	        System.out.println("Failed to load .env: " + e.getMessage());
	        adminPasswd = "defaultPassword";
	    }
	}

	// For testing
	public static void setAdminPasswd(String passwd) {
    	adminPasswd = passwd;
	}
	
	static Scanner scanner = new Scanner(System.in);
	
	public static void registerAdmin() {

		String pass = "";

		if(testMode) {
			System.out.print("Provide admin password: ");
			pass = scanner.nextLine();
		} else {
			// Hide passwd - only for CLI
			Console console = System.console();
			if (console != null) {
				char[] passwd = console.readPassword("Provide admin password: ");
				pass = new String(passwd);
			}

		}

		if(pass.equals(adminPasswd)) {
			if(testMode) {
				System.out.println("Granted access");
			} else {
				adminMenu();
			}
		} else {
			System.out.println("Invalid Password");
		}

	}

	static void showAdminMenu() {
		System.out.println("\n===== ADMIN MODE =====");
		System.out.println("1. View All Users");
		System.out.println("2. Edit User");
		System.out.println("3. Delete User");
		System.out.println("4. Exit");
		System.out.println("Choose an option: ");
	}

	static void adminMenu() {

		while (true) {
			showAdminMenu();
			int choice = ConsoleHelper.getIntInput();

			switch (choice) {
				case 1 -> UserService.listUsers();
				case 2 -> userEdition();
				case 3 -> userDeletion();
				case 4 -> {return;}
				default -> System.out.println("Invalid option");
			}
		}
	}

	static List<User> userDeletion() {

		int uid;
		System.out.println("Type user id You want to delete: ");

		uid = ConsoleHelper.getIntInput();

		boolean found = false;

		for (User u : Vault.users) {
			if (u.id == uid) {
				found = true;
				System.out.println("Deleting - " + u);
				System.out.println("Type [y] to confirm");
				char choice = scanner.next().charAt(0);
				scanner.nextLine(); 
				if (choice == 'y') {
					Vault.users.remove(u);
					return Vault.users;
				} else {
					return Vault.users;
				}
			}
		}
		if (!found) System.out.println("No match found.");
		return Vault.users;
	}

	static List<User> userEdition() {
		
		int uid;
		System.out.println("Type user id to edit: ");

		uid = ConsoleHelper.getIntInput();

		boolean found = false;
		String newName, newEmail;
		int newAge;

		for (User u : Vault.users) {
			if (u.id == uid) {
				found = true;
				System.out.println("Selected: " + u);

				while (true) {
					try{
						System.out.println("Type new Name: ");
						System.out.println(u.name);
						newName = scanner.nextLine();
						InputSanitizer.validateName(newName);
						break;
					} catch (ValidationException ex) {
						System.out.println(ex.getMessage());
					} 
				} 
				while (true) {
					try{
						System.out.println("Type new Email: ");
						System.out.println(u.email);
						newEmail = scanner.nextLine();
						InputSanitizer.validateEmail(newEmail);
						break;
					} catch(ValidationException ex) {
						System.out.println(ex.getMessage());
					} 
				} 

				while (true) {
					try{
						System.out.println("Type new Age: ");
						System.out.println(u.age);
						newAge = ConsoleHelper.getIntInput();
						InputSanitizer.validateAge(newAge);
						break;
					} catch (NumberFormatException ex) {
						System.out.println("Enter valid number.");
					} catch (ValidationException ex) {
						System.out.println(ex.getMessage());
					} 
				} 
				u.name = newName;
				u.email = newEmail;
				u.age = newAge;
				System.out.println("Appended new user data.");
				return Vault.users;
			}
		}
		if (!found) System.out.println("No match found.");
		return Vault.users;
	}
}