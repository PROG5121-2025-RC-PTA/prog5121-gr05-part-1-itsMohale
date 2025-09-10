 
import javax.swing.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;

public class Chatt {

    static List<Message> sentMessages = new ArrayList<>();
    static List<Message> sendLaterMessages = new ArrayList<>();
    static int messageCounter = 0;

    static List<User> users = new ArrayList<>(); // List of user objects

    static final String USERS_FILE = "users.json";

    public static void main(String[] args) {
        loadUsers();

        String[] options = {"Login", "Register"};
        int choice = JOptionPane.showOptionDialog(null,
                "Welcome to QuickChat!",
                "Welcome",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        boolean authenticated = false;
        if (choice == 0) {
            authenticated = loginUser();
        } else if (choice == 1) {
            authenticated = registerUser();
            if (authenticated) {
                JOptionPane.showMessageDialog(null, "Registration successful! Please login now.");
                authenticated = loginUser();
         }
        }

        if (authenticated) {
            showMainMenu();
        } else {
            JOptionPane.showMessageDialog(null, "Login failed or cancelled. Exiting application.");
       }
    }

    static void loadUsers() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(USERS_FILE)) {
            Type type = new TypeToken<List<User>>(){}.getType();
            users = gson.fromJson(reader, type);
            if (users == null) {
                users = new ArrayList<>();
            }
        } catch (IOException e) {
            users = new ArrayList<>();
        }
    }

    static void saveUsers() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean loginUser() {
        for (int attempts = 0; attempts < 3; attempts++) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();

            panel.add(new JLabel("Username:"));
            panel.add(userField);
            panel.add(new JLabel("Password:"));
            panel.add(passField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Login to QuickChat",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (isValidLogin(username, password)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Try again.");
                }
            } else {
                return false; // cancel
            }
        }
        return false; // too many attempts
    }

    static boolean isValidLogin(String username, String password) {
        for (User u : users) {
            if (u.username.equals(username) && u.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    static boolean registerUser() {
        while (true) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();
            JPasswordField passConfirmField = new JPasswordField();
            JTextField phoneField = new JTextField();

            panel.add(new JLabel("Choose Username:"));
            panel.add(userField);
            panel.add(new JLabel("Choose Password:"));
            panel.add(passField);
            panel.add(new JLabel("Confirm Password:"));
            panel.add(passConfirmField);
            panel.add(new JLabel("Phone Number (+27XXXXXXXXXX):"));
            panel.add(phoneField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Register New User",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                String username = userField.getText().trim();
                String password = new String(passField.getPassword());
                String confirm = new String(passConfirmField.getPassword());
                String phone = phoneField.getText().trim();

                if (username.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username, password and phone cannot be empty.");
                    continue;
                }

                if (!phonenum(phone)) {
                    JOptionPane.showMessageDialog(null, "Phone number must be in format +27 followed by 10 digits.");
                    continue;
                }

                if (nameExists(username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists. Choose another.");
                    continue;
                }

                if (!password.equals(confirm)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match. Try again.");
                    continue;
                }

                users.add(new User(username, password, phone));
                saveUsers();
                return true;
            } else {
                return false; // cancel
            }
     }
    }

    static boolean nameExists(String username) {
        for (User u : users) {
            if (u.username.equals(username)) {
                return true;
            }
       }
        return false;
    }

    static boolean phonenum(String phone) {
        return phone != null && phone.matches("\\+27\\d{10}");
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
        } while (!validatePhone(phoneNumber));

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

 
    static class User {
        String username;
        String password;
        String phone;

        public User(String username, String password, String phone) {
            this.username = username;
            this.password = password;
            this.phone = phone;
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