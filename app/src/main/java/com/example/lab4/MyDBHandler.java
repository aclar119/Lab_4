package com.example.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/** A class which allows for simple communication with the SQLite database.
 * @author Andrew Clarke
 * @author Geordan Coutts
 * @version 1.0 (11/25/2020)
 * @since version 1.0
 */
public class MyDBHandler extends SQLiteOpenHelper {

    // defining the schema
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCTNAME = "productname";
    private static final String COLUMN_PRICE = "price";

    /**
     * Creates a MyDBHandler object, which extends SQLiteOpenHelper
     * @param context context
     */
    public MyDBHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creates the table for the products. Includes the product ID, name, and price.
     * @param db A database in which the table will be created.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table TABLE_PRODUCTS (COLUMN_ID integer primary key, COLUMN_PRODUCTNAME TEXT,
        // COLUMN_PRICE DOUBLE)
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS
                + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PRODUCTNAME + " TEXT,"
                + COLUMN_PRICE + " DOUBLE" +
                ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    /**
     * Deletes the old tables and creates a new one. The tables may be changed by incrementing the
     * database version number.
     * @param db The database to be upgraded.
     * @param oldVersion The old database's version number.
     * @param newVersion The new database's version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    /**
     * Inserts a product into the database.
     * @param product The Product instance to be inserted.
     */
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        // creating an empty set of values
        ContentValues values = new ContentValues();
        // add values to the set
        values.put(COLUMN_PRODUCTNAME, product.getProductName());
        values.put(COLUMN_PRICE, product.getPrice());

        // insert the set into the products table and close
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }

    /**
     * Searches the database for a product with a particular name. If a product with a matching name
     * is found, it is returned. If no product is found, a null Product object is returned.
     * @param productName The name that is being searched.
     * @return the product if found or null if not found
     */
    // find a product from database
    public Product findProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();

        // run a query to find the product with the specified product name
        // SELECT * FROM TABLE_PRODUCTS WHERE COLUMN_PRODUCTNAME = "productname"
        String query = "SELECT * FROM " + TABLE_PRODUCTS
                + " WHERE " + COLUMN_PRODUCTNAME
                + " = \"" + productName + "\"";

        // passing the query
        Cursor cursor = db.rawQuery(query, null);

        Product product = new Product();

        // moves cursor to the first row
        if (cursor.moveToFirst()) {
            product.setID((Integer.parseInt(cursor.getString(0))));
            product.setProductName(cursor.getString(1));
            product.setPrice(Double.parseDouble(cursor.getString(2)));
            cursor.close();
        } else {
            product = null;
        }
        db.close();

        // we return the first product in the query result with the specified product name
        // or null if there is no product with that name
        return product;
    }

    /**
     * Searches for and deletes a product from the database.
     * @param productName The name of the product to be deleted.
     * @return A boolean. True if the value was successfully deleted and false if not.
     */
    public boolean deleteProduct(String productName) {
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();

        // run a query to find the product with the specified name, then delete
        // SELECT * FROM TABLE_PRODUCTS WHERE COLUMN_PRODUCTNAME = "productName"
        String query = "SELECT * FROM " + TABLE_PRODUCTS
                + " WHERE " + COLUMN_PRODUCTNAME
                + " = \"" + productName + "\"";

        // passing the query
        Cursor cursor = db.rawQuery(query, null);

        // moves cursor to the first row
        // this deletes the first occurrence of the product with the specified name
        if (cursor.moveToFirst()) {
            String idStr = cursor.getString(0);
            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = " + idStr, null);
            cursor.close();
            result = true;
        }
        db.close();

        // if product is deleted this returns true
        return result;
    }

    /**
     * Presents all the data from the database.
     * @return Cursor containing all products from the table
     */
    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS;

        // returns all products from table
        return db.rawQuery(query, null);
    }

    /**
     * Reads all products from the table.
     * @return An ArrayList of products
     */
    // read all from table
    public ArrayList<Product> readProducts() {
        SQLiteDatabase db = this.getReadableDatabase();

        // passing the query
        Cursor cursorProducts = db.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);

        // create an arraylist for our products
        ArrayList<Product> productArrayList = new ArrayList<>();

        // while there are products in our table, keep moving to the next product
        // we add the product id, name, and price for each new element in the arraylist
        // column 0 is product id, column 1 is product name, column 2 is product price in our table
        if (cursorProducts.moveToFirst()) {
            do {
                productArrayList.add(new Product(cursorProducts.getInt(0),
                        cursorProducts.getString(1),
                        cursorProducts.getDouble(2)));
            }
            while (cursorProducts.moveToNext());
        }
        cursorProducts.close();
        return productArrayList;
    }
}
