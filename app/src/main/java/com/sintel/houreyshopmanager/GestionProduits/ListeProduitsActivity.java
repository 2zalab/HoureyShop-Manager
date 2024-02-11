package com.sintel.houreyshopmanager.GestionProduits;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sintel.houreyshopmanager.MainActivity;
import com.sintel.houreyshopmanager.Models.MaBaseDeDonneesHelper;
import com.sintel.houreyshopmanager.Models.Product;
import com.sintel.houreyshopmanager.R;

import java.util.ArrayList;
import java.util.List;

public class ListeProduitsActivity extends AppCompatActivity {
RecyclerView recyclerView;
ProductAdapter productAdapter;
private MaBaseDeDonneesHelper dbHelper;
    private ImageView btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_produits);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter();
        recyclerView.setAdapter(productAdapter);
        btnback=findViewById(R.id.Id_back_produit);


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListeProduitsActivity.this, MainActivity.class));
                finish();
            }
        });


// Récupérez la liste des produits et mettez-la à jour dans l'Adapter
        List<Product> productList = getProductsFromDatabase();
        productAdapter.setProductList(productList);
    }

    private List<Product> getProductsFromDatabase() {

        dbHelper = new MaBaseDeDonneesHelper(this);

        List<Product> productList = new ArrayList<>();

        // Obtenez une référence à votre base de données
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Définissez les colonnes que vous souhaitez récupérer
        String[] projection = {
                "id",
                "name",
                "description",
                "price",
                "quantity",
                "category"
        };

        // Effectuez la requête pour récupérer les produits de la base de données
        Cursor cursor = db.query(
                "products",  // Nom de la table
                projection,  // Colonnes à récupérer
                null,  // Clause WHERE
                null,  // Arguments de la clause WHERE
                null,  // GROUP BY
                null,  // HAVING
                null   // ORDER BY
        );

        // Parcourez le curseur pour récupérer les données des produits
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
            String quantity = cursor.getString(cursor.getColumnIndexOrThrow("quantity"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));

            // Créez un objet Product à partir des données récupérées
            Product product = new Product(id, name, description, price, quantity, category);

            // Ajoutez le produit à la liste des produits
            productList.add(product);
        }

        // Fermez le curseur et la base de données
        cursor.close();
        db.close();

        // Retournez la liste des produits
        return productList;
    }
}


