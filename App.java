 import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter cellphone number: ");
        String cell = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        // Call registerUser
        checkUser.registerUser(username, cell, password);
    }
    
    public class checkUser {

    //  Method for user name
    public static boolean checkUserName(String username) {
        if (username.length() > 5 && username.contains("_")) {
            return true;
        } else {
            System.out.println("Wrong input (username must be more than 5 characters and contain an underscore)");
            return false;
        }
    }

    // The phone number 
    public static boolean checkPhoneNum(String cellNumber) {
        if (cellNumber.length() != 13) {
            System.out.println("Wrong input (cell number must be 10 characters and '+27')");
            return false;
        }

        if (!cellNumber.startsWith("+27")) {
            System.out.println("Wrong input (cell number must start with +27)");
            return false;
        }

        if (cellNumber.charAt(3) != '0') {
            System.out.println("Wrong input (4th character must be 0)");
            return false;
        }

        char fifthChar = cellNumber.charAt(4);
        if (!Character.isDigit(fifthChar) || Character.getNumericValue(fifthChar) < 6) {
            System.out.println("Wrong input (5th digit must be ≥ 6 & < = 8)");
            return false;
        }

        return true;
    }

    // How the Password must be stracture
    public static boolean checkPword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{9,}$";
        if (password.matches(regex)) {
            return true;
        } else {
            System.out.println("Wrong input (password must be > 8 chars and include uppercase, lowercase, number, and symbol)");
            return false;
        }
    }

    // registration
    public static boolean registerUser(String username, String cellNumber, String password) {
        boolean validUsername = checkUserName(username);
        boolean validCell = checkPhoneNum(cellNumber);
        boolean validPassword = checkPword(password);

        if (validUsername && validCell && validPassword) {
            System.out.println("Welcom to ChitChat! " + username);
            return true;
        } else {
            System.out.println("Log in Faild, Please fix the above errors.");
            return false;
        }
    }
}
}
