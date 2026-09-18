import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

/** Handles transaction storage and transaction operations. */
public class ExpenseManager {
    private final ArrayList<Expense> transactions = new ArrayList<>();
    private final File dataFile = new File("data/expenses.txt");

    public ExpenseManager() {
        loadTransactions();
    }

    public ArrayList<Expense> getTransactions() {
        return transactions;
    }

    public void addTransaction(String type, double amount, String category,
                               String description, String date) {
        int nextId = 1;
        for (Expense transaction : transactions) {
            nextId = Math.max(nextId, transaction.getId() + 1);
        }
        transactions.add(new Expense(nextId, type, amount, category, description, date));
        saveTransactions();
    }

    public boolean deleteTransaction(int id) {
        for (int index = 0; index < transactions.size(); index++) {
            if (transactions.get(index).getId() == id) {
                transactions.remove(index);
                saveTransactions();
                return true;
            }
        }
        return false;
    }

    public ArrayList<Expense> searchTransactions(String searchText) {
        ArrayList<Expense> matches = new ArrayList<>();
        String search = searchText.toLowerCase(Locale.ROOT);
        for (Expense transaction : transactions) {
            if (transaction.toString().toLowerCase(Locale.ROOT).contains(search)) {
                matches.add(transaction);
            }
        }
        return matches;
    }

    private void loadTransactions() {
        try {
            File parent = dataFile.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            if (!dataFile.exists()) {
                dataFile.createNewFile();
                return;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split("\\|", -1);
                    if (fields.length != 6) {
                        continue;
                    }
                    try {
                        transactions.add(new Expense(Integer.parseInt(fields[0]), fields[1],
                                Double.parseDouble(fields[2]), fields[3], fields[4], fields[5]));
                    } catch (NumberFormatException ignored) {
                        // Ignore a damaged row and keep the application usable.
                    }
                }
            }
        } catch (IOException error) {
            System.out.println("Could not load transaction data: " + error.getMessage());
        }
    }

    private void saveTransactions() {
        try {
            File parent = dataFile.getParentFile();
            if (parent != null) {
                parent.mkdirs();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
                for (Expense transaction : transactions) {
                    writer.write(transaction.getId() + "|" + transaction.getType() + "|"
                            + transaction.getAmount() + "|" + clean(transaction.getCategory()) + "|"
                            + clean(transaction.getDescription()) + "|" + transaction.getDate());
                    writer.newLine();
                }
            }
        } catch (IOException error) {
            System.out.println("Could not save transaction data: " + error.getMessage());
        }
    }

    private String clean(String value) {
        return value.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }
}
