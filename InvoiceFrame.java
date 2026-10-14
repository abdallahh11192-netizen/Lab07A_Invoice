import javax.swing.*;
import java.awt.*;

public class InvoiceFrame extends JFrame {
    private JTextField titleField = new JTextField("INVOICE", 18);
    private JTextArea addressField = new JTextArea("Sam's Small Appliances\n100 Main Street\nAnytown, CA 98765", 3, 22);
    private JTextField nameField = new JTextField(15);
    private JTextField quantityField = new JTextField(5);
    private JTextField priceField = new JTextField(7);
    private JTextArea outputArea = new JTextArea(16, 55);
    private Invoice invoice;

    public InvoiceFrame() {
        super("Lab07A Invoice");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        form.add(new JLabel("Invoice Title:")); form.add(titleField);
        form.add(new JLabel("Customer Address:")); form.add(new JScrollPane(addressField));
        form.add(new JLabel("Product Name:")); form.add(nameField);
        form.add(new JLabel("Quantity:")); form.add(quantityField);
        form.add(new JLabel("Unit Price ($):")); form.add(priceField);

        JButton addButton = new JButton("Add Item");
        JButton newButton = new JButton("New Invoice");
        JPanel buttons = new JPanel();
        buttons.add(addButton); buttons.add(newButton);
        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        addButton.addActionListener(e -> addItem());
        newButton.addActionListener(e -> {
            invoice = new Invoice(titleField.getText(), addressField.getText());
            outputArea.setText(invoice.getDisplayText());
        });
        invoice = new Invoice(titleField.getText(), addressField.getText());
        outputArea.setText(invoice.getDisplayText());
        pack();
        setLocationRelativeTo(null);
    }

    private void addItem() {
        try {
            String name = nameField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            if (name.isEmpty() || quantity <= 0 || !Double.isFinite(price) || price < 0)
                throw new IllegalArgumentException();
            if (invoice.getAmountDue() == 0 && outputArea.getText().contains("AMOUNT DUE: $0.00")) {
                invoice = new Invoice(titleField.getText(), addressField.getText());
            }
            invoice.addItem(new LineItem(new Product(name, price), quantity));
            outputArea.setText(invoice.getDisplayText());
            nameField.setText(""); quantityField.setText(""); priceField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Enter a valid quantity and price.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Enter a product name, positive quantity, and nonnegative price.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvoiceFrame().setVisible(true));
    }
}
