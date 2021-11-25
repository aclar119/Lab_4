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
     * Creates a product and assigns it an id, productName, and price.
     * @param id the id of the product, use for identification within the database
     * @param productName the name of the product
     * @param price the price of the product
     */
    public Product(int id, String productName, double price) {
        this.id = id;
        this.productName = productName;
        this.price = price;
    }

    /**
     * Creates a product without assigning it an id.
     * @param productName the name of the product
     * @param price the price of the product
     */
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
