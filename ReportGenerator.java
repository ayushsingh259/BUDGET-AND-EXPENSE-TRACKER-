import java.util.HashMap;
import java.util.Map;

/** Creates totals and category summaries from the transaction list. */
public class ReportGenerator {
    public double totalIncome(ExpenseManager expenseManager) {
        return totalOfType(expenseManager, "INCOME");
    }

    public double totalExpenses(ExpenseManager expenseManager) {
        return totalOfType(expenseManager, "EXPENSE");
    }

    public double balance(ExpenseManager expenseManager) {
        return totalIncome(expenseManager) - totalExpenses(expenseManager);
    }

    public HashMap<String, Double> spendingByCategory(ExpenseManager expenseManager) {
        HashMap<String, Double> totals = new HashMap<>();
        for (Expense transaction : expenseManager.getTransactions()) {
            if (transaction.getType().equals("EXPENSE")) {
                totals.put(transaction.getCategory(),
                        totals.getOrDefault(transaction.getCategory(), 0.0) + transaction.getAmount());
            }
        }
        return totals;
    }

    public String highestSpendingCategory(ExpenseManager expenseManager) {
        String highestCategory = "None";
        double highestAmount = 0;
        for (Map.Entry<String, Double> entry : spendingByCategory(expenseManager).entrySet()) {
            if (entry.getValue() > highestAmount) {
                highestCategory = entry.getKey();
                highestAmount = entry.getValue();
            }
        }
        return highestCategory;
    }

    private double totalOfType(ExpenseManager expenseManager, String type) {
        double total = 0;
        for (Expense transaction : expenseManager.getTransactions()) {
            if (transaction.getType().equals(type)) {
                total += transaction.getAmount();
            }
        }
        return total;
    }
}
