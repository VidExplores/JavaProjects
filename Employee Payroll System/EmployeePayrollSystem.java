import java.sql.*;
import java.util.Scanner;

public class EmployeePayrollSystem {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; 
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Add a new employee
    public static void addEmployee(String name, String position, double salaryPerHour) {
        String query = "INSERT INTO employees (name, position, salary_per_hour) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, position);
            pstmt.setDouble(3, salaryPerHour);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new employee was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting employee: " + e.getMessage());
        }
    }

    // View all employees
    public static void viewEmployees() {
        String query = "SELECT * FROM employees";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("ID\tName\tPosition\tSalary/Hour");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + 
                                   rs.getString("position") + "\t" + rs.getDouble("salary_per_hour"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving employees: " + e.getMessage());
        }
    }

    public static void updateEmployeeSalary(int id, double newSalary) {
        String query = "UPDATE employees SET salary_per_hour = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setDouble(1, newSalary);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee salary updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error updating salary: " + e.getMessage());
        }
    }

    public static void deleteEmployee(int id) {
        String query = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Employee deleted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
        }
    }

    public static void calculatePayroll(int employeeId, double hoursWorked, double deductions) {
        String selectQuery = "SELECT salary_per_hour FROM employees WHERE id = ?";
        String insertQuery = "INSERT INTO payroll (employee_id, hours_worked, deductions, final_salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); 
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery); 
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
            
            selectStmt.setInt(1, employeeId);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                double salaryPerHour = rs.getDouble("salary_per_hour");
                double grossSalary = hoursWorked * salaryPerHour;
                double finalSalary = grossSalary - deductions;
                
                insertStmt.setInt(1, employeeId);
                insertStmt.setDouble(2, hoursWorked);
                insertStmt.setDouble(3, deductions);
                insertStmt.setDouble(4, finalSalary);
                
                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Payroll calculated and stored successfully!");
                    System.out.println("Gross Salary: " + grossSalary);
                    System.out.println("Final Salary (after deductions): " + finalSalary);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error calculating payroll: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            System.out.println("\nEmployee Payroll System:");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee Salary");
            System.out.println("4. Delete Employee");
            System.out.println("5. Calculate Payroll");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter employee name: ");
                    String name = sc.next();
                    System.out.print("Enter position: ");
                    String position = sc.next();
                    System.out.print("Enter salary per hour: ");
                    double salary = sc.nextDouble();
                    addEmployee(name, position, salary);
                    break;

                case 2:
                    viewEmployees();
                    break;

                case 3:
                    System.out.print("Enter employee ID to update salary: ");
                    int idToUpdate = sc.nextInt();
                    System.out.print("Enter new salary per hour: ");
                    double newSalary = sc.nextDouble();
                    updateEmployeeSalary(idToUpdate, newSalary);
                    break;

                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    int idToDelete = sc.nextInt();
                    deleteEmployee(idToDelete);
                    break;

                case 5:
                    System.out.print("Enter employee ID for payroll: ");
                    int employeeId = sc.nextInt();
                    System.out.print("Enter hours worked: ");
                    double hoursWorked = sc.nextDouble();
                    System.out.print("Enter deductions: ");
                    double deductions = sc.nextDouble();
                    calculatePayroll(employeeId, hoursWorked, deductions);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
