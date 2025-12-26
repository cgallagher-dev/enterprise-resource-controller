package a00321687_booze;

import java.io.Serializable;

// needs to be serializable
public class BoozeItem implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// attributes for item
    private String id;
    private String name;
    private String brand;
    private String type;
    private double price;
    private int quantityOnHand;

    // default constructor
    public BoozeItem() {
    }

    // constructor to create a new item with all details
    public BoozeItem(String id, String name, String brand, String type, double price, int quantityOnHand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.price = price;
        this.quantityOnHand = quantityOnHand;
    }

    // get/set the private variables

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    // method to get all details as a string
    public String getDetails() {
        return "ID: " + id + ", Name: " + name + ", Brand: " + brand + ", Price: " + price + ", Qty: " + quantityOnHand;
    }
}