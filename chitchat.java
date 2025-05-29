import javax.swing.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;

public class chitchat {

    static Scanner sc = new Scanner(System.in);
    static List<Message> sentMessages = new ArrayList<>();
    static List<Message> sendLaterMessages = new ArrayList<>();
    static int messageCounter = 0;

    public static void main(String[] args) {
        showMainMenu();
}

    static void showMainMenu() {
        while (true) {
            String[] options = {"Send Message", "View Messages", "Quit"};
            int choice = JOptionPane.showOptionDialog(null, "Welcome to QuickChat!", "Main Menu",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                handleSendMessage();
            } else if (choice == 1) {
                showSummary();
            } else if (choice == 2 || choice == JOptionPane.CLOSED_OPTION) {
                JOptionPane.showMessageDialog(null, "Exiting QuickChat. Goodbye!");
                break;
                      }
     }
    }

    static void handleSendMessage() {
        String recipientName = JOptionPane.showInputDialog("Enter recipient's name:");
        if (recipientName == null) return;

        String phoneNumber;
        do {
            phoneNumber = JOptionPane.showInputDialog("Enter recipient's phone number (+27XXXXXXXXX):");
            if (phoneNumber == null) return;
            if (!validatePhone(phoneNumber)) {
                JOptionPane.showMessageDialog(null, "Invalid phone number. Must be +27 followed by 9 digits.");
            }
 } 
        while (!validatePhone(phoneNumber));

        int numMessages = 0;
        while (true) {
            String input = JOptionPane.showInputDialog("How many messages would you like to send? (1–10)");
            if (input == null) return;
            try {
                numMessages = Integer.parseInt(input);
                if (numMessages >= 1 && numMessages <= 10) break;
                else JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 10.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid number. Try again.");
                }
  }

        for (int i = 0; i < numMessages; i++) {
            String message;
            do {
                message = JOptionPane.showInputDialog("Enter message " + (i + 1) + " (max 250 chars):");
                if (message == null) return;
                if (!validateMessage(message)) {
                    JOptionPane.showMessageDialog(null, "Message must be 250 characters or less.");
                }
            } while (!validateMessage(message));

            String id = generateId();
            String hash = createHash(id, message, i + 1);

            Message msg = new Message(id, phoneNumber, message, hash);

            int option = JOptionPane.showOptionDialog(null,
                    "Message ID: " + id + "\nHash: " + hash + "\nTo: " + phoneNumber + "\nMessage: " + message,
                    "Review Message " + (i + 1),
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Send", "Disregard", "Send Later"},
                    null);

            if (option == 0) {
                sentMessages.add(msg);
            } else if (option == 2) {
                sendLaterMessages.add(msg);
  }
 }

        saveMessagesToJson(sentMessages, "sentMessages.json");
    }

    static boolean validatePhone(String phone) {
        return phone != null && phone.matches("\\+27\\d{9}");
    }

    static boolean validateMessage(String msg) {
        return msg != null && msg.length() <= 250;
    }

    static String generateId() {
        return String.format("%010d", ++messageCounter);
    }

    static String createHash(String id, String msg, int number) {
        String[] words = msg.trim().split("\\s+");
        String first = words.length > 0 ? words[0] : "";
        String last = words.length > 1 ? words[words.length - 1] : first;
        return id.substring(0, 2) + ":" + number + ":" + (first + last).toUpperCase();
    }

    static void showSummary() {
        if (sentMessages.isEmpty() && sendLaterMessages.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No messages to display.");
            return;
        }

        StringBuilder summary = new StringBuilder("Session Messages:\n\n");

        for (Message m : sentMessages) {
            summary.append("To: ").append(m.recipient)
                    .append("\nMessage: ").append(m.message)
                    .append("\nHash: ").append(m.hash).append("\n\n");
        }

        for (Message m : sendLaterMessages) {
            summary.append("[SEND LATER] To: ").append(m.recipient)
                    .append("\nMessage: ").append(m.message)
                    .append("\nHash: ").append(m.hash).append("\n\n");
        }

        int total = sentMessages.size() + sendLaterMessages.size();
        summary.append("Total messages this session: ").append(total);

        JOptionPane.showMessageDialog(null, summary.toString(), "Session Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    static void saveMessagesToJson(List<Message> messages, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(messages, writer);
        } catch (IOException e) {
            e.printStackTrace();
 }
 }

    static class Message {
        String id;
        String recipient;
        String message;
        String hash;

        public Message(String id, String recipient, String message, String hash) {
            this.id = id;
            this.recipient = recipient;
            this.message = message;
            this.hash = hash;
   }
}
}

