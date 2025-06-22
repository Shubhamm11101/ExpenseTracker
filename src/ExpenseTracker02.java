import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;

class Expense{
    private String description;
    private double amount;
    private String category;
    private Date date;

    public Expense(String description, double amount, String category, Date date) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return description + " | Amount: $" + amount + " | Category: " + category + " | Date: " + formatter.format(date);
    }
}

public class ExpenseTracker02 {
    private List<Expense> expenses;
    private double monthlyLimit;
    private double currentMonthlyTotal;
    private Scanner scanner;
    private Connection connection;

    public ExpenseTracker02() {
        expenses = new ArrayList<>();
        scanner = new Scanner(System.in);
        connectToDatabase();
        setMonthlyLimit();
        loadExpensesFromDatabase();
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/expenses"; // Update with your database URL
            String user = "root"; // Update with your DB username
            String password = "Shubham@2003"; // Update with your DB password
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setMonthlyLimit() {
        System.out.print("Set your monthly expense limit: ");
        this.monthlyLimit = scanner.nextDouble();
        scanner.nextLine(); // consume the newline
    }

    private void loadExpensesFromDatabase() {
        try {
            String sql = "select * from expense";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String description = resultSet.getString("description");
                double amount = resultSet.getDouble("amount");
                String category = resultSet.getString("category");
                Date date = resultSet.getDate("date");

                expenses.add(new Expense(description, amount, category, date));
                currentMonthlyTotal += amount;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }

    public void addExpense() {
        System.out.print("Enter expense description: ");
        String description = scanner.nextLine();
        System.out.print("Enter expense amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume the newline
        System.out.print("Enter expense category: ");
        String category = scanner.nextLine();
        Date date = new Date(); // Current date

        // Check if adding this expense exceeds the limit
        if (currentMonthlyTotal + amount > monthlyLimit) {
            System.out.println("Adding this expense exceeds your monthly limit!");
        } else {
            try {
                String sql = "INSERT INTO expense (description, amount, category, date) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, description);
                statement.setDouble(2, amount);
                statement.setString(3, category);
                statement.setDate(4, new java.sql.Date(date.getTime()));

                statement.executeUpdate();
                expenses.add(new Expense(description, amount, category, date));
                currentMonthlyTotal += amount;
                System.out.println("Expense added.");
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Notify if limit is reached
        if (currentMonthlyTotal >= monthlyLimit) {
            System.out.println("Notification: You have reached your monthly limit!");
        }
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("Expenses:");
            for (int i = 0; i < expenses.size(); i++) {
                System.out.println((i + 1) + ". " + expenses.get(i));
            }
        }
    }

    public void viewExpensesByCategory() {
        System.out.print("Enter category to filter by: ");
        String category = scanner.nextLine();
        boolean found = false;

        System.out.println("Expenses in category '" + category + "':");
        for (Expense expense : expenses) {
            if (expense.getCategory().equalsIgnoreCase(category)) {
                System.out.println(expense);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No expenses found in this category.");
        }
    }

    public void generateMonthlyReceipt() {
        System.out.println("Monthly Expense Receipt:");
        double total = 0;
        for (Expense expense : expenses) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy");
            String monthYear = formatter.format(expense.getDate());
            total += expense.getAmount();
            System.out.println(expense + " | Month: " + monthYear);
        }
        System.out.println("Total for the month: $" + total);
    }

    public void generateYearlyReceipt() {
        System.out.println("Yearly Expense Receipt:");
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
            System.out.println(expense);
        }
        System.out.println("Total for the year: $" + total);
    }

    public void run() {
        while (true) {
            System.out.println("\nExpense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Expenses by Category");
            System.out.println("4. Generate Monthly Receipt");
            System.out.println("5. Generate Yearly Receipt");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    viewExpensesByCategory();
                    break;
                case 4:
                    generateMonthlyReceipt();
                    break;
                case 5:
                    generateYearlyReceipt();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    try {
                        if (connection != null) connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void main(String[] args) {
        ExpenseTracker02 tracker = new ExpenseTracker02();
        tracker.run();
    }
}
