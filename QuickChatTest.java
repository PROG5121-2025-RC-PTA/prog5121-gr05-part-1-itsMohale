import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class chitchat {

    @Test
    public void testMessageLengthSuccess() {
        String message = "This is under 250 characters.";
        assertTrue(chitchat.validateMessage(message));
        System.out.println("Message ready to send.");
    }

    @Test
    public void testMessageLengthFailure() {
        String message = "a".repeat(251);
        assertFalse(chitchat.validateMessage(message));
        int excess = message.length() - 250;
        System.out.println("Message exceeds 250 characters by " + excess + ", please reduce size.");
    }

    @Test
    public void testValidPhoneNumber() {
        String phone = "+27718693002";
        assertTrue(chitchat.validatePhone(phone));
        System.out.println("Cell phone number successfully captured.");
    }

    @Test
    public void testInvalidPhoneNumber() {
        String phone = "08575975889";
        assertFalse(chitchat.validatePhone(phone));
        System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
    }

    @Test
    public void testHashGeneration() {
        String id = "0000000000";
        String message = "Hi Mike, can you join us for dinner tonight";
        int number = 0;
        String expectedHash = "00:0:HITONIGHT";
        String actualHash = chitchat.createHash(id, message, number);
        assertEquals(expectedHash, actualHash);
    }

    @Test
    public void testMessageIdGeneration() {
        chitchat.messageCounter = 0; // Reset counter
        String id = chitchat.generateId();
        assertEquals("0000000001", id);
        System.out.println("Message ID generated: " + id);
    }

    @Test
    public void testMessageActionSend() {
        String result = getActionMessage(0);
        assertEquals("Message successfully sent.", result);
    }

    @Test
    public void testMessageActionDiscard() {
        String result = getActionMessage(1);
        assertEquals("Press 0 to delete message.", result);
    }

    @Test
    public void testMessageActionStore() {
        String result = getActionMessage(2);
        assertEquals("Message successfully stored.", result);
    }

 
    private String getActionMessage(int option) {
        switch (option) {
            case 0: return "Message successfully sent.";
            case 1: return "Press 0 to delete message.";
            case 2: return "Message successfully stored.";
            default: return "Unknown action.";
        }
    }
}
