package com.example.lab4;

/**
 * @author Andrew Clarke
 * @author Geordan Coutts
 * @version 1.0 (11/25/2020)
 * @since version 1.0
 */
public class Product {
    private int id;
    private String productName;
    private double price;

    // constructors
    public Product() {

    }

    public Product(int id, String productName, double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }

    public Product (String productName, double price) {
        this.productName = productName;
        this.price = price;
    }


    // setters and getters
    public void setID(int id) { this.id = id; }
    public int getId() { return this.id; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductName() {return this.productName; }
    public void setPrice(double price) { this.price = price; }
    public double getPrice() { return this.price; }
}
