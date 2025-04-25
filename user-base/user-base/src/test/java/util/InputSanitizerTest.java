package util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputSanitizerTest {
    
    // Name testing
    @Test
    void accepts_valid_name() {
        assertDoesNotThrow(() -> InputSanitizer.validateName("George"));
    }

    @Test
    void rejects_empty_name() {
        Exception ex = assertThrows(RuntimeException.class, () -> 
            InputSanitizer.validateName("")
        );
        assertEquals("Must be at least 2 non-whitespace characters.", ex.getMessage());
    }
   
    @Test
    void rejects_to_short_name() {
        Exception ex = assertThrows(RuntimeException.class, () -> 
            InputSanitizer.validateName("q")
        );
        assertEquals("Must be at least 2 non-whitespace characters.", ex.getMessage());
    }

    // Email testing
    @Test
    void accepts_valid_email() {
        assertDoesNotThrow(() -> InputSanitizer.validateEmail("George@op.io"));
    }

    @Test
    void rejects_missing_at() {
        Exception ex = assertThrows(RuntimeException.class, () -> 
            InputSanitizer.validateEmail("George.io")
        );
        assertEquals("Email must contain '@' and '.'", ex.getMessage());
    }

    @Test
    void rejects_missing_dot() {
        Exception ex = assertThrows(RuntimeException.class, () -> 
            InputSanitizer.validateEmail("George@op")
        );
        assertEquals("Email must contain '@' and '.'", ex.getMessage());
    }

    // Age testing
    @Test
    void accepts_valid_age() {
        assertDoesNotThrow(() -> InputSanitizer.validateAge(17));
    }

    @Test
    void rejects_wrong_age() {
        Exception ex = assertThrows(RuntimeException.class, () -> 
            InputSanitizer.validateAge(170)
        );
        assertEquals("Age must be between 1 and 120.", ex.getMessage());
    }

}