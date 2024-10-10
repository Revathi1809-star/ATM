import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private static Map<String, User> users = new HashMap<>();

    static {
        // Sample users
        users.put("user1", new User("user1", "1234", new Account(1000.00)));
        users.put("user2", new User("user2", "5678", new Account(2000.00)));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        User user = authenticate(userId, pin);
        if (user != null) {
            manageATM(scanner, user);
        } else {
            System.out.println("Invalid User ID or PIN.");
        }

        scanner.close();
    }

    private static User authenticate(String userId, String pin) {
        User user = users.get(userId);
        return (user != null && user.getPin().equals(pin)) ? user : null;
    }

    private static void manageATM(Scanner scanner, User user) {
        Account account = user.getAccount();
        while (true) {
            System.out.println("ATM Menu:");
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Transfer");
            System.out.println("4. Transaction History");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful!");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful!");
                    break;
                case 3:
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    System.out.print("Enter target user ID: ");
                    String targetUserId = scanner.next();
                    User targetUser = users.get(targetUserId);
                    if (targetUser != null) {
                        if (account.transfer(targetUser.getAccount(), transferAmount)) {
                            System.out.println("Transfer successful!");
                        } else {
                            System.out.println("Insufficient balance.");
                        }
                    } else {
                        System.out.println("Invalid target user ID.");
                    }
                    break;
                case 4:
                    System.out.println("Transaction History:");
                    new TransactionHistory(account.getTransactionHistory()).printHistory();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
