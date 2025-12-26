package a00321687_booze;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class InventoryServer {

    public static void main(String[] args) {
        
        try {
            // print a message to show the server is starting
            System.out.println("Server: Starting inventory server...");
            
            // create the rmi registry on port 1234 (as per moodle sample)
            Registry r = LocateRegistry.createRegistry(1234);
            
            // create instance of InventoryManager
            // will also load the "inventory.ser" file
            InventoryManager manager = new InventoryManager();
            
            // bind manager object to a name in the registry
            // client will look for "inventoryservice"
            r.bind("inventoryservice", manager);
            
            System.out.println("Server: Inventory server running!");
            
        } catch (Exception e) {
            System.out.println("Server: Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}