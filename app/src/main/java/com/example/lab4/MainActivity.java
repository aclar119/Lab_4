package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText priceBox;
    Button viewProductsBtn;

    ListView productList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set variables to the IDs of .xml elements
        idView = (TextView) findViewById(R.id.textView_ProductID);
        productBox = (EditText) findViewById(R.id.editText_ProductName);
        priceBox = (EditText) findViewById(R.id.editText_Price);
        productList = (ListView) findViewById(R.id.productListView);
        viewProductsBtn = (Button) findViewById(R.id.button_viewall);

        MyDBHandler dbhandler = new MyDBHandler(this);
        listItem = new ArrayList<>();

        // call the viewData() method to display the existing products
        viewData();

        // when a product in the list is clicked, a toast is displayed with the name of the product
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = productList.getItemAtPosition(i).toString();
                Toast.makeText(MainActivity.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // we use onClick for the Add button in our layout to call this method
    public void newProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);

        // get price from the text box
        double price = Double.parseDouble(priceBox.getText().toString());

        // get product name from the text box
        // use the constructor from Product.java
        Product product = new Product(productBox.getText().toString(), price);

        // add to database with the addProduct() method from MyDBHandler.java
        dbHandler.addProduct(product);

        // clear the text boxes
        productBox.setText("");
        priceBox.setText("");

        // clearing the list of products
        // calling viewData() method to display the updates list of products
        // this means what is displayed in the ListView is always current
        listItem.clear();
        viewData();
    }

    // we use onClick for the Find button in our layout to call this method
    public void lookupProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);

        // get product in the database using findProduct() method from MyDBHandler.java
        Product product = dbHandler.findProduct(productBox.getText().toString());

        // if found, then display the product details
        // if not, display "No Match Found"
        if (product != null) {
            idView.setText(String.valueOf((product.getId())));
            priceBox.setText(String.valueOf(product.getPrice()));
        } else {
            idView.setText("No Match Found");
        }
    }

    // we use onClick for the Delete button in our layout to call this method
    public void removeProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);

        // delete product in the database using deleteProduct() method from MyDBHandler.java
        boolean result = dbHandler.deleteProduct(productBox.getText().toString());

        // clearing the list of products
        // calling viewData() method to display tje updated list of products
        // this means what is displayed in the ListView is always current
        listItem.clear();
        viewData();

        // "Record Delted" or "No Match Found"
        if (result) {
            idView.setText("Record Deleted");
            productBox.setText("");
            priceBox.setText("");
        } else {
            idView.setText("No Match Found");
        }
    }

    // we use onClick for the View All button in our layout to call this method
    public void viewProducts(View view) {
        // move from one activity page to the activity_display_product page
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

    private void viewData() {
        MyDBHandler dbHandler = new MyDBHandler(this);

        // call the viewData() method in MyDBHandler that runs the query
        Cursor cursor = dbHandler.viewData();

        // if there are no products in the table, a toast says there is no data to show
        // otherwise, while there are products, keep moving to the next product
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to show", Toast.LENGTH_SHORT).show();
        } else {
            while(cursor.moveToNext()) {
                listItem.add(cursor.getString(1));
            }
            // create an array adapter that provides a view for each item
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

            // attaching the adapter to the ListView
            productList.setAdapter(adapter);
        }
    }
}