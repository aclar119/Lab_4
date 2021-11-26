package com.example.lab4;

/**
 * A class which acts as the template for a Product. A product may be constructed with or without an id.
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

    /**
     * Creates an empty Product object
     */
    public Product() {

    }

    /**
     * Create a new product object and set its attributes
     * @param id int representing the database id (primary key) for this product
     * @param productName String representing the name of the product
     * @param price double representing the price of the product
     */
    public Product(int id, String productName, double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }

    /**
     * Create a new product object and set its name and price attributes
     * @param productName String representing the name of the product
     * @param price double representing the price of the product
     */
    public Product (String productName, double price) {
        this.productName = productName;
        this.price = price;
    }

    /**
     * Returns a string describing the product by listing all its information.
     * @returns String A string of all the Product's data.
     */
    public String productInfo() {
        return ("Product ID: " + this.id + "Product Name: " + this.productName + "; Price: $" + this.price);
    }


    // setters and getters
    public void setID(int id) { this.id = id; }
    public int getId() { return this.id; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getProductName() {return this.productName; }
    public void setPrice(double price) { this.price = price; }
    public double getPrice() { return this.price; }
}
