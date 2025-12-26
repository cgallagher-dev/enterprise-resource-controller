package a00321687_booze;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

// import these for rmi and the list
import java.rmi.RemoteException;
import java.util.ArrayList;

// main window class
public class InventoryGUI extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// rmi object
    private InventoryInterface inventoryManager;

    // gui components
    private JTextArea displayArea = new JTextArea(20, 50);

    // labels and text fields for adding/editing item
    private JTextField idField = new JTextField(10);
    private JTextField nameField = new JTextField(10);
    private JTextField brandField = new JTextField(10);
    private JTextField typeField = new JTextField(10);
    private JTextField priceField = new JTextField(10);
    private JTextField quantityField = new JTextField(10);

    // all buttons
    private JButton refreshButton = new JButton("Refresh List");
    private JButton addButton = new JButton("Add Item");
    private JButton deleteButton = new JButton("Delete Item (Use ID)");
    private JButton updateButton = new JButton("Update Item (Use ID)");

    // constructor
    public InventoryGUI(InventoryInterface rmiStub) {
        
        // store rmi connection
        this.inventoryManager = rmiStub;

        // set up window
        setTitle("Liqour Store Inventory Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); // main layout

        // create panels
        JScrollPane scrollPane = new JScrollPane(displayArea);
        displayArea.setEditable(false); // so you can't type in it by accident
        add(scrollPane, BorderLayout.CENTER); // add to the middle

        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 5, 5)); // 7 rows, 2 columns
        
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Brand:"));
        inputPanel.add(brandField);
        inputPanel.add(new JLabel("Type:"));
        inputPanel.add(typeField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        
        // empty label for spacing
        inputPanel.add(new JLabel("")); 

        add(inputPanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5)); // 4 rows, 1 column
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        
        add(buttonPanel, BorderLayout.EAST);

        // action listeners
        refreshButton.addActionListener(this);
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        updateButton.addActionListener(this);
        
        pack(); // sizes window to fit components
        setVisible(true); // makes the window show up

        // load the inventory on startup
        refreshInventoryDisplay();
    }

    @SuppressWarnings("exports")
	@Override
    public void actionPerformed(ActionEvent e) {
        
        // check which button was pressed (the "controller" of MVC)
        if (e.getSource() == refreshButton) {
            refreshInventoryDisplay();
        } 
        else if (e.getSource() == addButton) {
            addItem();
        } 
        else if (e.getSource() == deleteButton) {
            deleteItem();
        } 
        else if (e.getSource() == updateButton) {
            updateItem();
        }
    }


    // gets the list from the server and updates the text area
    private void refreshInventoryDisplay() {
        try {
            // clear the old text
            displayArea.setText("");
            
            // get the most recent list from the server
            ArrayList<BoozeItem> list = inventoryManager.getInventory();
            
            // loop through list and print
            for (BoozeItem item : list) {
                displayArea.append(item.getDetails() + "\n");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            displayArea.setText("Error: Failed to connect to server.");
        }
    }

    // read all fields and tell server to add a new item
    private void addItem() {
        try {
            // get data from fields
            String id = idField.getText();
            String name = nameField.getText();
            String brand = brandField.getText();
            String type = typeField.getText();
            // convert price and quantity
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            // create new item
            BoozeItem newItem = new BoozeItem(id, name, brand, type, price, quantity);
            
            // send it to server
            inventoryManager.addItem(newItem);
            
            // refresh list to show change
            refreshInventoryDisplay();
            
            // clear input fields
            clearInputFields();

        } catch (RemoteException e) {
            e.printStackTrace();
            displayArea.setText("Error: Failed to add item.");
        } catch (NumberFormatException nfe) {
            // happens if price/quantity are not valid numbers
            JOptionPane.showMessageDialog(this, "Error: Price and quantity must be numbers.");
        }
    }

    // read the id field and tell server to delete
    private void deleteItem() {
        try {
            // get the id of the item to delete
            String id = idField.getText();
            
            // tell the server to delete it
            inventoryManager.deleteItem(id);
            
            // refresh the list
            refreshInventoryDisplay();
            
            // clear the input fields
            clearInputFields();

        } catch (RemoteException e) {
            e.printStackTrace();
            displayArea.setText("Error: Failed to delete item.");
        }
    }

    // reads all fields and tells server to update an item
    private void updateItem() {
        try {
            // get all the data from the fields
            String id = idField.getText();
            String name = nameField.getText();
            String brand = brandField.getText();
            String type = typeField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            
            // create an updated item object
            BoozeItem updatedItem = new BoozeItem(id, name, brand, type, price, quantity);

            // send the id and the new item details to the server
            inventoryManager.updateItem(id, updatedItem);
            
            // refresh the list
            refreshInventoryDisplay();
            
            // clear the input fields
            clearInputFields();

        } catch (RemoteException e) {
            e.printStackTrace();
            displayArea.setText("Error: Failed to update item.");
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Error: Price and quantity must be numbers.");
        }
    }

    // method to clear all text boxes
    private void clearInputFields() {
        idField.setText("");
        nameField.setText("");
        brandField.setText("");
        typeField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }
}