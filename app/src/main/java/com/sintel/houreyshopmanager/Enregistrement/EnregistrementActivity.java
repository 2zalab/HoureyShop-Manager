package com.sintel.houreyshopmanager.Enregistrement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
// Importer les classes nécessaires
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.sintel.houreyshopmanager.Models.MaBaseDeDonneesHelper;
import com.sintel.houreyshopmanager.R;
import com.sintel.houreyshopmanager.login.LoginActivity;

import android.widget.TextView;
import android.widget.Toast;

public class EnregistrementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrement);

        // Récupérer les références des vues
        EditText loginEditText = findViewById(R.id.login_enregistrement);
        EditText passwordEditText = findViewById(R.id.password_enregistrement);
        Button enregistrerButton = findViewById(R.id.btn_enregistrer);
        TextView LinkToSign = findViewById(R.id.LinkToLoginId);


        // on clic sur se Connecter pour etre rediriger sur la page de connexion.
        LinkToSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnregistrementActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

      // Ajouter un écouteur de clic sur le bouton "Enregistrer"
        enregistrerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs saisies dans les champs de texte
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(login.isEmpty() || password.isEmpty()){
                    // Afficher une boîte de dialogue indiquant que tous les champs doivent être remplis
                    AlertDialog.Builder builder = new AlertDialog.Builder(EnregistrementActivity.this);
                    builder.setTitle("Erreur");
                    builder.setMessage("Remplir tous les champs SVP!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                // Créer une instance de la classe SQLiteOpenHelper pour accéder à la base de données
                MaBaseDeDonneesHelper dbHelper = new MaBaseDeDonneesHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                // Créer un objet ContentValues pour stocker les valeurs à insérer dans la base de données
                ContentValues values = new ContentValues();
                values.put("login", login);
                values.put("password", password);
                // Insérer les valeurs dans la table "utilisateurs"
                long result = db.insert("utilisateurs", null, values);
                // Vérifier si l'insertion a réussi
                if (result != -1) {
                    // Afficher une boîte de dialogue indiquant que tous les champs doivent être remplis
                    AlertDialog.Builder builder = new AlertDialog.Builder(EnregistrementActivity.this);
                    builder.setTitle("Message");
                    builder.setMessage("Enregistrement réussi! Bien vouloir vous connectez maintenant.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                   // Toast.makeText(getApplicationContext(), "Enregistrement réussi", Toast.LENGTH_SHORT).show();
                    loginEditText.setText("");
                    passwordEditText.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();
                }

                // Fermer la connexion à la base de données
                db.close();
            }
            }
        });

    }
}