import java.sql.*;
import java.util.Scanner;

public class OnlineExaminationSystem {

    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String DB_USERNAME = "username"; 
    private static final String DB_PASSWORD = "password"; 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            System.out.println("Welcome to the Online Examination System!");
            System.out.println("Please log in to continue.");
            System.out.print("Username: ");
            String username = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (authenticateUser(connection, username, password)) {
                System.out.println("Login successful! Starting the exam...");

                int score = conductExam(connection, scanner);

                System.out.println("Exam completed!");
                System.out.println("Your score: " + score + " points");
            } else {
                System.out.println("Invalid username or password. Please try again.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            scanner.close();
        }
    }

    private static boolean authenticateUser(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int conductExam(Connection connection, Scanner scanner) throws SQLException {
        String query = "SELECT * FROM questions";
        int score = 0;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                String questionText = rs.getString("question_text");
                String optionA = rs.getString("option_a");
                String optionB = rs.getString("option_b");
                String optionC = rs.getString("option_c");
                String optionD = rs.getString("option_d");
                String correctOption = rs.getString("correct_option");

                System.out.println("\nQuestion " + questionId + ": " + questionText);
                System.out.println("A. " + optionA);
                System.out.println("B. " + optionB);
                System.out.println("C. " + optionC);
                System.out.println("D. " + optionD);

                System.out.print("Your answer: ");
                String userAnswer = scanner.nextLine().toUpperCase();

                if (userAnswer.equals(correctOption)) {
                    score++;
                }
            }
        }
        return score;
    }
}
