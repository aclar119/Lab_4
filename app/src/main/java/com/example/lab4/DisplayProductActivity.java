package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

/** A class which
 * @author Andrew Clarke
 * @author Geordan Coutts
 * @version 1.0 (11/25/2020)
 * @since version 1.0
 */
public class DisplayProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);

        // initializing variables
        ArrayList<Product> productArrayList = new ArrayList<>();
        MyDBHandler dbHandler = new MyDBHandler(this);

        // getting the arraylist of products from MyDBHandler class
        productArrayList = dbHandler.readProducts();

        // here we pass the ArrayList to our adapter class
        ProductAdapter productAdapter = new ProductAdapter(productArrayList, this);

        // our recycler view is idProductDisplay in the activity_display_product.xml file
        RecyclerView productsRV = findViewById(R.id.idProductDisplay);

        // layout manager positions items within our recyclerview
        // using a vertical recyclerview (other option is horizontal)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        productsRV.setLayoutManager(linearLayoutManager);

        // attaching the adapter to the recyclerview
        productsRV.setAdapter(productAdapter);

    }
}