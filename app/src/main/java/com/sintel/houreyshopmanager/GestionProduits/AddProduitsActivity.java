package com.sintel.houreyshopmanager.GestionProduits;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.sintel.houreyshopmanager.MainActivity;
import com.sintel.houreyshopmanager.Models.MaBaseDeDonneesHelper;
import com.sintel.houreyshopmanager.R;
import com.sintel.houreyshopmanager.login.LoginActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProduitsActivity extends AppCompatActivity {
    private EditText editTextProductName;
    private EditText editTextProductDescription;
    private EditText editTextProductPrice;
    private EditText editTextProductQuantity;
    private Spinner spinnerProductCategory;
    private Button buttonAddProduct;
    private ImageView btnback;
    private MaBaseDeDonneesHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produits);

        databaseHelper = new MaBaseDeDonneesHelper(this);

        editTextProductName = findViewById(R.id.editTextProductName);
        editTextProductDescription = findViewById(R.id.editTextProductDescription);
        editTextProductPrice = findViewById(R.id.editTextProductPrice);
        editTextProductQuantity = findViewById(R.id.editTextProductQuantity);
        spinnerProductCategory = findViewById(R.id.spinnerProductCategory);
        buttonAddProduct = findViewById(R.id.buttonAddProduct);
        btnback=findViewById(R.id.Id_back);

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProduitsActivity.this, MainActivity.class));
                finish();
            }
        });

        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = editTextProductName.getText().toString();
                String productDescription = editTextProductDescription.getText().toString();
                String productPrice = editTextProductPrice.getText().toString();
                String productQuantity = editTextProductQuantity.getText().toString();
                String productCategory = spinnerProductCategory.getSelectedItem().toString();

                if (productName.isEmpty() || productDescription.isEmpty() || productCategory.isEmpty()||productPrice.isEmpty()||productQuantity.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddProduitsActivity.this);
                    builder.setTitle("Erreur");
                    builder.setIcon(R.drawable.ic_baseline_info_24);
                    builder.setMessage("Remplir tous les champs !");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();

                    ContentValues values = new ContentValues();
                    values.put(MaBaseDeDonneesHelper.COLUMN_NAME, productName);
                    values.put(MaBaseDeDonneesHelper.COLUMN_DESCRIPTION, productDescription);
                    values.put(MaBaseDeDonneesHelper.COLUMN_PRICE, productPrice);
                    values.put(MaBaseDeDonneesHelper.COLUMN_QUANTITY, productQuantity);
                    values.put(MaBaseDeDonneesHelper.COLUMN_CATEGORY, productCategory);

                    long newRowId = db.insert(MaBaseDeDonneesHelper.TABLE_PRODUCTS, null, values);

                    if (newRowId != -1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddProduitsActivity.this);
                        builder.setTitle("Message");
                        builder.setIcon(R.drawable.ok);
                        builder.setMessage("Produit ajouté avec succès !");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        // Toast.makeText(AddProduitsActivity.this, "Produit ajouté avec succès", Toast.LENGTH_SHORT).show();
                        // Réinitialiser les champs de saisie
                        editTextProductName.setText("");
                        editTextProductDescription.setText("");
                        editTextProductPrice.setText("");
                        editTextProductQuantity.setText("");
                        spinnerProductCategory.setSelection(0);

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddProduitsActivity.this);
                        builder.setTitle("Erreur");
                        builder.setIcon(R.drawable.ic_baseline_info_24);
                        builder.setMessage("Erreur lors de l'ajout du produit !");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                       // Toast.makeText(AddProduitsActivity.this, ""+productName+productPrice+productDescription+productQuantity+productCategory, Toast.LENGTH_SHORT).show();
                    }
                    db.close();
                }
            }
        });
    }
}