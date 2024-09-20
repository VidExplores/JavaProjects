import java.sql.*;
import java.util.Scanner;

class BankAccount {
    private int accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(int accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }
}

public class BankingSystemApp {
    private static Connection connect() throws SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "userid","password123");
    }

    public static void createAccount(int accountNumber, String holder, double initialDeposit) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO accounts (account_number, holder, balance) VALUES (?, ?, ?)")) {
            pstmt.setInt(1, accountNumber);
            pstmt.setString(2, holder);
            pstmt.setDouble(3, initialDeposit);
            pstmt.executeUpdate();
        }
    }

    public static BankAccount getAccount(int accountNumber) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM accounts WHERE account_number = ?")) {
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new BankAccount(rs.getInt("account_number"), rs.getString("holder"), rs.getDouble("balance"));
            }
        }
        return null;
    }

    public static void updateAccountBalance(int accountNumber, double newBalance) throws SQLException {
        try (Connection conn = connect(); 
             PreparedStatement pstmt = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE account_number = ?")) {
            pstmt.setDouble(1, newBalance);
            pstmt.setInt(2, accountNumber);
            pstmt.executeUpdate();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter account number: ");
                        int accountNumber = scanner.nextInt();
                        System.out.print("Enter account holder name: ");
                        String holder = scanner.next();
                        System.out.print("Enter initial deposit: ");
                        double deposit = scanner.nextDouble();
                        createAccount(accountNumber, holder, deposit);
                        System.out.println("Account created successfully!");
                        break;
                    case 2:
                        System.out.print("Enter account number: ");
                        accountNumber = scanner.nextInt();
                        BankAccount account = getAccount(accountNumber);
                        if (account != null) {
                            System.out.print("Enter amount to deposit: ");
                            double amount = scanner.nextDouble();
                            account.deposit(amount);
                            updateAccountBalance(accountNumber, account.getBalance());
                            System.out.println("Deposit successful! New balance: " + account.getBalance());
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;
                    case 3:
                        System.out.print("Enter account number: ");
                        accountNumber = scanner.nextInt();
                        account = getAccount(accountNumber);
                        if (account != null) {
                            System.out.print("Enter amount to withdraw: ");
                            double withdrawalAmount = scanner.nextDouble();
                            if (account.withdraw(withdrawalAmount)) {
                                updateAccountBalance(accountNumber, account.getBalance());
                                System.out.println("Withdrawal successful! New balance: " + account.getBalance());
                            } else {
                                System.out.println("Insufficient balance.");
                            }
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;
                    case 4:
                        System.out.print("Enter account number: ");
                        accountNumber = scanner.nextInt();
                        account = getAccount(accountNumber);
                        if (account != null) {
                            System.out.println("Account holder: " + account.getAccountHolder());
                            System.out.println("Current balance: " + account.getBalance());
                        } else {
                            System.out.println("Account not found.");
                        }
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice, try again.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
