package a00321687_booze;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMain {

    public static void main(String[] args) {
        
        System.out.println("Client: Starting client...");
        
        try {
            // connect to the rmi registry on port 1234 (as in sample on moodle)
            Registry r = LocateRegistry.getRegistry("localhost", 1234);
            
            // look up the remote object by its name ("inventoryservice")
            InventoryInterface inventoryManager = (InventoryInterface) r.lookup("inventoryservice");
            
            System.out.println("Client: Connected to server.");
            
            // create the gui
            new InventoryGUI(inventoryManager);
            
            System.out.println("Client: GUI started.");
            
        } catch (Exception e) {
            // error handling
            System.out.println("Client: Error connecting to server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}