package a00321687_booze;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

// add all the imports for file i/o
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

// implements interface and is the main rmi object
public class InventoryManager extends UnicastRemoteObject implements InventoryInterface {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// main list
    private ArrayList<BoozeItem> inventoryList;
    // file name to save to
    private final String filename = "inventory.ser";

    // class constructor
    public InventoryManager() throws RemoteException {
        super();
        
        loadInventory();
    }

    // implement all methods from interface

    @Override
    public ArrayList<BoozeItem> getInventory() throws RemoteException {
        System.out.println("Server: Client asked for inventory. Sending list...");
        return inventoryList;
    }

    @Override
    public void addItem(BoozeItem item) throws RemoteException {
        System.out.println("Server: Client wants to add an item.");
        inventoryList.add(item);
        // temporary print to server console to check it's working
        System.out.println("Server: Added: " + item.getDetails());
        
        // save any changes to the file
        saveInventory();
    }

    @Override
    public void deleteItem(String id) throws RemoteException {
        System.out.println("Server: Client wants to delete item with ID: " + id);
        
        // use removeif to find the item with this id and remove it
        inventoryList.removeIf(item -> item.getId().equals(id));
        System.out.println("Server: Delete complete.");

        // save any changes
        saveInventory();
    }

    @Override
    public void updateItem(String id, BoozeItem updatedItem) throws RemoteException {
        System.out.println("Server: Client wants to update item with ID: " + id);
        
        // loop through the list to find the item to update
        for (int i = 0; i < inventoryList.size(); i++) {
            if (inventoryList.get(i).getId().equals(id)) {
                // replace the old item with the new one
                inventoryList.set(i, updatedItem);
                System.out.println("Server: Update complete.");
                break;
            }
        }
        
        // save any changes
        saveInventory();
    }

    // serialisation methods

    private void saveInventory() {
        System.out.println("Server: Saving inventory to " + filename);
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            
            // write the arraylist object to the file
            objectOut.writeObject(inventoryList);
            
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
	private void loadInventory() {
        System.out.println("Server: Attempting to load from " + filename);
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            // read the arraylist object back
            inventoryList = (ArrayList<BoozeItem>) objectIn.readObject();
            
            objectIn.close();
            fileIn.close();
            System.out.println("Server: Successfully loaded inventory.");
        } catch (FileNotFoundException e) {
            System.out.println("Server: " + filename + " not found. Creating new list...");
            inventoryList = new ArrayList<>();
            // initial save of the empty list
            saveInventory();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Server: Error loading file. creating new list.");
            e.printStackTrace();
            inventoryList = new ArrayList<>();
        }
    }
}