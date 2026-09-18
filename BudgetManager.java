import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** Stores and calculates the single monthly budget used by the application. */
public class BudgetManager {
    private double budget;
    private final File budgetFile = new File("data/budget.txt");

    public BudgetManager() {
        loadBudget();
    }

    public void setBudget(double budget) {
        this.budget = budget;
        saveBudget();
    }

    public double getBudget() {
        return budget;
    }

    public double getSpent(ExpenseManager expenseManager) {
        double spent = 0;
        for (Expense transaction : expenseManager.getTransactions()) {
            if (transaction.getType().equals("EXPENSE")) {
                spent += transaction.getAmount();
            }
        }
        return spent;
    }

    public double getRemaining(ExpenseManager expenseManager) {
        return budget - getSpent(expenseManager);
    }

    public boolean isExceeded(ExpenseManager expenseManager) {
        return budget > 0 && getSpent(expenseManager) > budget;
    }

    private void loadBudget() {
        try {
            File parent = budgetFile.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            if (!budgetFile.exists()) {
                budgetFile.createNewFile();
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(budgetFile))) {
                String line = reader.readLine();
                if (line != null && !line.trim().isEmpty()) {
                    budget = Double.parseDouble(line.trim());
                }
            }
        } catch (IOException | NumberFormatException error) {
            budget = 0;
            System.out.println("Could not load budget data. Starting with no budget.");
        }
    }

    private void saveBudget() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(budgetFile))) {
            writer.write(Double.toString(budget));
        } catch (IOException error) {
            System.out.println("Could not save budget data: " + error.getMessage());
        }
    }
}
