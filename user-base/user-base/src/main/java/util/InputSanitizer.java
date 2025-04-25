package util;

public class InputSanitizer {
    public static void validateName(String name) {
        if (name == null || name.length() < 2 || name.trim().isEmpty()) {
            throw new ValidationException("Must be at least 2 non-whitespace characters.");
        }
    }

    public static void validateEmail(String email) {
        if (!email.contains("@") || !email.contains(".")) {
            throw new ValidationException("Email must contain '@' and '.'");
        }
    }

    public static void validateAge(int age) {
        if (age <= 0 || age >= 120) {
            throw new ValidationException("Age must be between 1 and 120.");
        }
    }
}