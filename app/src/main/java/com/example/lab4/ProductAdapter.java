package com.example.lab4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/** A class which
 * @author Andrew Clarke
 * @author Geordan Coutts
 * @version 1.0 (11/25/2020)
 * @since version 1.0
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    // adapter is used to get data from the table and then populate the recyclerview
    // think of it as the middle man that connects the table with the layout view

    private ArrayList<Product> productArrayList;
    private Context context;

    // constructor
    public ProductAdapter(ArrayList<Product> productModalArrayList, Context context) {
        this.productArrayList = productModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    // this is called when the recyclerview needs to represent an item
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file for our recycler view items
        // layout inflater is used to create a new product for our layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    // called by RecyclerView to display the data at the specified position
    // it updates the contents of the recycler view item to reflect the specific product
    public  void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productArrayList.get(position);

        // we get the product name using our getter from Product.java
        // then we set the text in the corresponding TextView element in our layout
        // process repeated for product price and id
        holder.productName.setText(product.getProductName());
        // we display data as text using setText() but price is a double and id is an int
        // so we use valueOf() to represent the values as a string
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productId.setText(String.valueOf(product.getId()));
    }

    @Override
    public int getItemCount() {
        // return the size of the ArrayList
        return productArrayList.size();
    }

    // an inner class called viewHolder provides the layout for an item
    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for the TextViews
        private TextView productName, productPrice, productId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initialize the TextViews
            // use findViewBy to find the view in our layout with the specified id
            productName = itemView.findViewById(R.id.idProductName);
            productPrice = itemView.findViewById(R.id.idProductPrice);
            productId = itemView.findViewById(R.id.idProductId);
        }
    }
}
