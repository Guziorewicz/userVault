package util;

import java.io.*;
import java.util.*;

public class ConsoleHelper {
    // Int checker
    public static Scanner scanner = new Scanner(System.in);

	public static int getIntInput() {
		while (true) {
			try {
				return Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException ex) {
				System.out.print("Invalid number. Try again: ");
			}
		}
	}
}