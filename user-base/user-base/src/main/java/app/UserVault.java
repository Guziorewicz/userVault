package app;

import model.User;
import service.UserService;
import service.AdminService;
import service.Vault;
import service.FileService;
import util.ConsoleHelper;
import util.InputSanitizer;
import util.ValidationException;
import java.io.*;
import java.util.*;

public class UserVault {
	static Scanner scanner = new Scanner(System.in);
	
	// Main memu
	public static void main(String[] args) {
		Vault.users = FileService.loadFromFile();
		while (true) {
			showMenu();
			int choice = ConsoleHelper.getIntInput();

			switch (choice) {
				case 1 -> UserService.registerUser();
				case 2 -> UserService.listUsers();
				case 3 -> UserService.searchUser();
				case 4 -> FileService.saveToFileAndExit();
				case 5 -> AdminService.registerAdmin();
				default -> System.out.println("Invalid option");
			}
		}
	}

	static void showMenu() {
		System.out.println("\n===== USER VAULT =====");
		System.out.println("1. Register New User");
		System.out.println("2. View All Users");
		System.out.println("3. Search by Name");
		System.out.println("4. Exit");
		System.out.println("5. Admin Panel");
		System.out.println("Choose an option: ");
	}
	
}
