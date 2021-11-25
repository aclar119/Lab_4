package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

/** A class which compiles all the functionality of the app. It acts as the central activity for all
 * user interaction.
 * @author Geordan Coutts
 * @version 1.0 (11/25/2020)
 * @since version 1.0
 */
public class MainActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText priceBox;
    Button viewProductsBtn;

    ListView productList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    /**
     * Displays the exisiting products.
     * @param savedInstanceState
     */
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

    /**
     * Gets the price and product name from the text boxes, and constructs an instance of the
     * Product class. The Product then gets added to the database and the text boxes are cleared.
     * Finally, the list of products is updated.
     * @param view Allows for use of onClick attribute in xml
     */
    public void newProduct (View view) {
        // we use onClick for the Add button in our layout to call this method
        MyDBHandler dbHandler = new MyDBHandler(this);

        // get product name from the text box
        String strName = productBox.getText().toString();
        // get price from the text box
        String strPrice = priceBox.getText().toString();

        // Validate name and price before proceeding
        if (validateName(strName) && validatePrice(strPrice)) {

            double price = Double.parseDouble(strPrice);

            // use the constructor from Product.java
            Product product = new Product(strName, price);

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
    }

    /**
     * Verifies whether the given String is a valid product name
     * @param Name The product name to be validated
     * @return Boolean describing whether the param Name is valid product name
     */
    public static boolean validateName(String Name) {
        return Name.matches("[a-zA-Z0-9\\s]+");

    }

    /**
     * Verifies whether the given String is a valid product price
     * @param Price The product price to be validated
     * @return Boolean describing whether the param Price is valid product price
     */
    public static boolean validatePrice(String Price) {
        return Price.matches("\\d+.?\\d{0,2}");
    }


    /**
     * Searches for the product in the database. If the product is found, the product's details are
     * displayed. Otherwise, text reading, "No Match Found" is displayed on screen.
     * @param view Allows for use of onClick attribute in xml
     */
    public void lookupProduct (View view) {
        // we use onClick for the Find button in our layout to call this method
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

    /**
     * Finds and deletes a product from the database. The product list view is then updated.
     * @param view Allows for use of onClick attribute in xml
     */
    public void removeProduct (View view) {
        // we use onClick for the Delete button in our layout to call this method
        MyDBHandler dbHandler = new MyDBHandler(this);

        // delete product in the database using deleteProduct() method from MyDBHandler.java
        boolean result = dbHandler.deleteProduct(productBox.getText().toString());

        // clearing the list of products
        // calling viewData() method to display tje updated list of products
        // this means what is displayed in the ListView is always current
        listItem.clear();
        viewData();

        // "Record Deleted" or "No Match Found"
        if (result) {
            idView.setText("Record Deleted");
            productBox.setText("");
            priceBox.setText("");
        } else {
            idView.setText("No Match Found");
        }
    }

    /**
     * Displays the complete list of all products and their data.
     * @param view Allows for use of onClick attribute in xml
     */
    public void viewProducts(View view) {
        // we use onClick for the View All button in our layout to call this method
        // move from one activity page to the activity_display_product page

        // Changed from original lab to open on new page instead
        // so that the back button works
        Intent intent = new Intent(this, DisplayProductActivity.class);
        startActivity(intent);
    }

    /**
     * Iterates through all the products in the database and displays them.
     */
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