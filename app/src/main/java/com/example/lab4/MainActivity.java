package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText priceBox;

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
}