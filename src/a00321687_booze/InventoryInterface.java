package a00321687_booze;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InventoryInterface extends Remote {

    // all methods in an rmi interface must throw remoteexception
    
    // send the whole list of items to the client
    public ArrayList<BoozeItem> getInventory() throws RemoteException;

    // let the client send a new item to the server to be added
    public void addItem(BoozeItem item) throws RemoteException;

    // let the client delete an item using its id
    public void deleteItem(String id) throws RemoteException;

    // let the client update an existing item
    public void updateItem(String id, BoozeItem updatedItem) throws RemoteException;

}