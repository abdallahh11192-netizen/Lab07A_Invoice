import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private String title;
    private String customerAddress;
    private List<LineItem> items = new ArrayList<>();

    public Invoice(String title, String customerAddress) {
        this.title = title;
        this.customerAddress = customerAddress;
    }

    public void addItem(LineItem item) { items.add(item); }
    public double getAmountDue() {
        double total = 0;
        for (LineItem item : items) total += item.getTotal();
        return total;
    }
    public String getDisplayText() {
        StringBuilder sb = new StringBuilder();
        sb.append("                 ").append(title).append("\n\n");
        sb.append(customerAddress).append("\n\n");
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("%-20s %5s %10s %10s%n", "Item", "Qty", "Price", "Total"));
        for (LineItem item : items) {
            sb.append(String.format("%-20.20s %5d $%9.2f $%9.2f%n",
                item.getProduct().getName(), item.getQuantity(),
                item.getProduct().getUnitPrice(), item.getTotal()));
        }
        sb.append("------------------------------------------------------------\n");
        sb.append(String.format("AMOUNT DUE: $%.2f%n", getAmountDue()));
        return sb.toString();
    }
}
