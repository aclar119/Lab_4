package com.example.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    // defining the schema
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCTNAME = "productname";
    private static final String COLUMN_PRICE = "price";

    // constructor
    public MyDBHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create the table
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

    // deletes old tables and creates a new one
    // change tables by incrementing the database version number
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    // insert into database
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

    // delete from database
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

    // read all data from table
    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS;

        // returns all products from table
        return db.rawQuery(query, null);
    }
}
