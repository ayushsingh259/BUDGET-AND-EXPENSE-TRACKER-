import java.util.Locale;

/** Represents one income or expense transaction. */
public class Expense {
    private int id;
    private String type;
    private double amount;
    private String category;
    private String description;
    private String date;

    public Expense(int id, String type, double amount, String category, String description, String date) {
        this.id = id;
        this.type = type.toUpperCase(Locale.ROOT);
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public int getId() { return id; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getDate() { return date; }

    public void setType(String type) { this.type = type.toUpperCase(Locale.ROOT); }
    public void setAmount(double amount) { this.amount = amount; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return id + " | " + type + " | " + String.format("%.2f", amount)
                + " | " + category + " | " + description + " | " + date;
    }
}
