import java.io.IOException;
import java.util.Scanner;

public class Admin {
    private final Scanner sc = new Scanner(System.in);
    private final String password = "Admin_123";

    public static void main(String[] args) throws IOException {
        Admin admin = new Admin();
        System.out.print("Enter administrator password: ");
        String attemptedPassword = admin.sc.nextLine();
        if (!admin.SignIn(attemptedPassword)) {
            System.out.println("Access denied.");
            return;
        }

        PlayerGroup group = new PlayerGroup("players.txt");
        boolean running = true;
        while (running) {
            int choice = admin.Options();
            switch (choice) {
                case 1:
                    System.out.println(ViewPlayers(group));
                    break;
                case 2:
                    admin.DeletePlayer(group);
                    break;
                case 3:
                    admin.ChangeBalance(group);
                    break;
                case 4:
                    group.savePlayers();
                    System.out.println("Changes saved. Goodbye.");
                    running = false;
                    break;
            }
        }
    }

    public Integer Options() {
        while (true) {
            System.out.print("\n========== Admin Menu ==========\n"
                    + "1. View all players.\n"
                    + "2. Delete a player by first and last name.\n"
                    + "3. Change a player's available cash balance.\n"
                    + "4. Save and exit.\n"
                    + "Changes are saved when you choose option 4.\n"
                    + "================================\n"
                    + "Enter your choice (1-4): ");
            try {
                int choice = Integer.parseInt(sc.nextLine().trim());
                if (choice >= 1 && choice <= 4) {
                    return choice;
                }
            } catch (NumberFormatException ex) {
                // Invalid text gets the same retry message as an invalid number.
            }
            System.out.println("Choose 1, 2, 3, or 4.");
        }
    }

    public boolean SignIn(String password) {
        // The password passed by main counts as the first of three attempts.
        for (int attempt = 1; attempt <= 3; attempt++) {
            if (password.equals(this.password)) {
                return true;
            }
            if (attempt < 3) {
                System.out.print("Wrong password. Try again: ");
                password = sc.nextLine();
            }
        }
        return false;
    }

    private static String ViewPlayers(PlayerGroup group) {
        return group.toString();
    }
    private boolean DeletePlayer(PlayerGroup group) {
        System.out.print("Enter player's first name: ");
        String fName = sc.nextLine();

        System.out.print("Enter player's last name: ");
        String lName = sc.nextLine();

        Player2 target = group.getPlayer(fName, lName);
        if (target == null) {
            System.out.println("Player not found.");
            return false;
        }
        System.out.println(target);
        System.out.print("Type DELETE to confirm: ");
        if (!sc.nextLine().equals("DELETE")) {
            System.out.println("Deletion canceled.");
            return false;
        }

        boolean removed = group.removePlayer(fName, lName);

        if (removed) {
            System.out.println("Player removed.");
        } else {
            System.out.println("Player not found.");
        }

        return removed;
    }
    private void ChangeBalance(PlayerGroup group) {
        System.out.print("Enter player's first name: ");
        String fName = sc.nextLine();
        System.out.print("Enter player's last name: ");
        String lName = sc.nextLine();
        Player2 target = group.getPlayer(fName, lName);
        if (target == null) {
            System.out.println("Player not found.");
            return;
        }

        System.out.println(target);
        System.out.println("This replaces available cash only; debt and round history stay unchanged.");
        double amount;
        while (true) {
            System.out.print("New cash balance (0 or greater): ");
            try {
                amount = Double.parseDouble(sc.nextLine());
                if (Double.isFinite(amount) && amount >= 0) {
                    break;
                }
            } catch (NumberFormatException ex) {
                // Ask again without changing the player's current balance.
            }
            System.out.println("Enter a finite number that is 0 or greater.");
        }

        System.out.printf("Change cash from $%.2f to $%.2f? Type yes to confirm: ",
                target.getMoney(), amount);
        if (!sc.nextLine().equalsIgnoreCase("yes")) {
            System.out.println("Balance change canceled.");
            return;
        }

        if (target.setAvailableMoney(amount)) {
            System.out.println("Cash balance updated.");
            System.out.println(target);
        } else {
            System.out.println("Invalid cash balance; nothing changed.");
        }
    }
}
