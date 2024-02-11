package com.sintel.houreyshopmanager.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseDeDonneesHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MaBaseDeDonnees.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_CATEGORY = "category";


    public MaBaseDeDonneesHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer la table "utilisateurs"
        String createTableQuery = "CREATE TABLE utilisateurs (id INTEGER PRIMARY KEY AUTOINCREMENT, login TEXT, password TEXT)";

        String createTableProduits = "CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, description TEXT, price TEXT, quantity TEXT, category TEXT)";

        String createProduitsTableQuery = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_PRICE + " TEXT," +
                COLUMN_QUANTITY + " TEXT," +
                COLUMN_CATEGORY + " TEXT" +
                ")";

        db.execSQL(createTableQuery);
        db.execSQL(createProduitsTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Mettre à jour la base de données si nécessaire
        if (oldVersion < newVersion) {
            // Supprimer les tables existantes
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
            db.execSQL("DROP TABLE IF EXISTS utilisateurs");
            // Recréer la table
            onCreate(db);
        }
    }

    public void deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(productId)});
        db.close();
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, product.getName());
        values.put(COLUMN_DESCRIPTION, product.getDescription());
        values.put(COLUMN_PRICE, product.getPrice());
        values.put(COLUMN_QUANTITY, product.getQuantity());
        values.put(COLUMN_CATEGORY, product.getCategory());

        db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(product.getId())});
        db.close();
    }
}