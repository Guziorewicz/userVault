package service;
import util.ConsoleHelper;
import util.InputSanitizer;
import util.ValidationException;
import service.Vault;
import model.User;
import java.io.*;
import java.util.*;

public class UserService {
    // User functions
    static Scanner scanner = new Scanner(System.in);

	public static void registerUser() {

		String name ="", email = "";
		int age;

		while (true) {
			try{
				System.out.println("Name: ");
				name = scanner.nextLine();
				InputSanitizer.validateName(name);
				break;
			} catch (ValidationException ex) {
				System.out.println(ex.getMessage());
			} 
		} 

		while (true) {
			try{
				System.out.println("Email: ");
				email = scanner.nextLine();
				InputSanitizer.validateEmail(email);
				break;
			} catch(ValidationException ex) {
				System.out.println(ex.getMessage());
			} 
		} 

		while (true) {
			try{
				System.out.println("Age: ");
				age = ConsoleHelper.getIntInput();
				InputSanitizer.validateAge(age);
				break;
			} catch (NumberFormatException ex) {
				System.out.println("Enter valid number.");
			} catch (ValidationException ex) {
				System.out.println(ex.getMessage());
			} 
		} 

		Vault.users.add(new User(name, email, age));
		System.out.println("User registered");
	}

	public static void listUsers() {
		if (Vault.users.isEmpty()) {
			System.out.println("No users yet.");
			return;
		}
		System.out.println("\n ---- Registered Users ----");
		for (User u : Vault.users) {
			System.out.println("- " + u);
		}
	}
	
	public static void searchUser() {
		System.out.println("Enter name to search: ");

		String query = scanner.nextLine().toLowerCase();

		boolean found = false;

		for (User u : Vault.users) {
			if (u.name.toLowerCase().contains(query)) {
				System.out.println("- " + u);
				found = true;
			}
		}
		if (!found) System.out.println("No match found.");
	}
}