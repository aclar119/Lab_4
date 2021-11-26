package com.example.lab4;

import org.junit.Test;
import static org.junit.Assert.*;

public class test_Product {

    // Testing the constructor and getters for the Product class
    private static Product testProduct = new Product(7, "Apple", 1.99);

    @Test
    public void testGetId() {
        assertEquals(7, testProduct.getId());
    }

    @Test
    public void testGetProductName() {
        assertEquals("Apple", testProduct.getProductName());
    }

    @Test
    public void testGetPrice() {
        assertEquals(1.99, testProduct.getPrice(), 0.001);
    }

    @Test
    public void testProductInfo() {
        assertEquals("Product ID: 7; Product Name: Apple; Price: $1.99", testProduct.productInfo());
    }
}
