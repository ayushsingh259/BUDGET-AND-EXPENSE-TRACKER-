import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/** Console entry point for ExpenseTrack. */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static ExpenseManager expenseManager;
    private static BudgetManager budgetManager;
    private static final ReportGenerator reportGenerator = new ReportGenerator();

    public static void main(String[] args) {
        expenseManager = new ExpenseManager();
        budgetManager = new BudgetManager();
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": addTransaction("EXPENSE"); break;
                case "2": addTransaction("INCOME"); break;
                case "3": viewTransactions(expenseManager.getTransactions()); break;
                case "4": deleteTransaction(); break;
                case "5": searchTransactions(); break;
                case "6": setBudget(); break;
                case "7": viewBudget(); break;
                case "8": showSummary(); break;
                case "9": running = false; break;
                default: System.out.println("Invalid choice. Please select an option from 1-9.");
            }
        }
        System.out.println("Thank you for using ExpenseTrack.");
    }

    private static void showMenu() {
        System.out.println("\n========================================");
        System.out.println("EXPENSETRACK - PERSONAL EXPENSE TRACKER");
        System.out.println("========================================");
        System.out.println("1. Add Expense");
        System.out.println("2. Add Income");
        System.out.println("3. View Transactions");
        System.out.println("4. Delete Transaction");
        System.out.println("5. Search Transactions");
        System.out.println("6. Set Monthly Budget");
        System.out.println("7. View Budget");
        System.out.println("8. Monthly Summary");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void addTransaction(String type) {
        double amount = readPositiveAmount();
        String category = readRequired("Category: ");
        String description = readRequired("Description: ");
        String date = readDate();
        expenseManager.addTransaction(type, amount, category, description, date);
        System.out.println(type.substring(0, 1) + type.substring(1).toLowerCase() + " added successfully.");
    }

    private static double readPositiveAmount() {
        while (true) {
            System.out.print("Amount: ");
            try {
                double amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount <= 0) {
                    System.out.println("Amount must be greater than zero.");
                } else {
                    return amount;
                }
            } catch (NumberFormatException error) {
                System.out.println("Invalid amount. Please enter a number.");
            }
        }
    }

    private static String readRequired(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty.");
        }
    }

    private static String readDate() {
        while (true) {
            String date = readRequired("Date (dd-MM-yyyy): ");
            try {
                LocalDate.parse(date, DATE_FORMAT);
                return date;
            } catch (DateTimeParseException error) {
                System.out.println("Invalid date. Use the format dd-MM-yyyy.");
            }
        }
    }

    private static void viewTransactions(ArrayList<Expense> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }
        System.out.println("\nID | TYPE | AMOUNT | CATEGORY | DESCRIPTION | DATE");
        for (Expense transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static void deleteTransaction() {
        int id = readInteger("Transaction ID to delete: ");
        if (expenseManager.deleteTransaction(id)) {
            System.out.println("Transaction deleted.");
        } else {
            System.out.println("Transaction not found.");
        }
    }

    private static void searchTransactions() {
        String search = readRequired("Search text: ");
        viewTransactions(expenseManager.searchTransactions(search));
    }

    private static void setBudget() {
        double budget = readPositiveAmount();
        budgetManager.setBudget(budget);
        System.out.println("Monthly budget saved.");
    }

    private static void viewBudget() {
        if (budgetManager.getBudget() <= 0) {
            System.out.println("No monthly budget has been set.");
            return;
        }
        printBudgetDetails();
    }

    private static void showSummary() {
        System.out.println("\n========================================");
        System.out.println("MONTHLY SUMMARY");
        System.out.println("========================================");
        System.out.printf("Total Income: %.2f%n", reportGenerator.totalIncome(expenseManager));
        System.out.printf("Total Expenses: %.2f%n", reportGenerator.totalExpenses(expenseManager));
        System.out.printf("Balance: %.2f%n", reportGenerator.balance(expenseManager));
        System.out.println("Number of Transactions: " + expenseManager.getTransactions().size());
        System.out.println("\nSpending by Category:");
        for (Map.Entry<String, Double> entry : reportGenerator.spendingByCategory(expenseManager).entrySet()) {
            System.out.printf("%s: %.2f%n", entry.getKey(), entry.getValue());
        }
        System.out.println("Highest Category: " + reportGenerator.highestSpendingCategory(expenseManager));
        printBudgetDetails();
    }

    private static void printBudgetDetails() {
        double spent = budgetManager.getSpent(expenseManager);
        double remaining = budgetManager.getRemaining(expenseManager);
        System.out.printf("\nBudget: %.2f%n", budgetManager.getBudget());
        System.out.printf("Spent: %.2f%n", spent);
        System.out.printf("Remaining: %.2f%n", remaining);
        if (budgetManager.isExceeded(expenseManager)) {
            System.out.printf("Warning: Budget exceeded by %.2f.%n", Math.abs(remaining));
        }
    }

    private static int readInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException error) {
                System.out.println("Invalid number. Please enter a whole number.");
            }
        }
    }
}
