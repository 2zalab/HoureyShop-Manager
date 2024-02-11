package com.sintel.houreyshopmanager.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.google.android.material.textfield.TextInputEditText;
import com.sintel.houreyshopmanager.Enregistrement.EnregistrementActivity;
import com.sintel.houreyshopmanager.MainActivity;
import com.sintel.houreyshopmanager.Models.MaBaseDeDonneesHelper;
import com.sintel.houreyshopmanager.R;

public class LoginActivity extends AppCompatActivity {
 TextView btn_forget_password,LinkSignUp;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         btn_forget_password= findViewById(R.id.PwdForgetId);
         LinkSignUp = findViewById(R.id.LinkToSignUpId);

        LinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, EnregistrementActivity.class);
                startActivity(intent);
            }
        });


        // Récupérer les références des vues
        TextInputEditText loginEditText = findViewById(R.id.LoginSignIn);
        TextInputEditText passwordEditText = findViewById(R.id.pwd);
        Button signInButton = findViewById(R.id.btn_signIn);

// Ajouter un écouteur de clic sur le bouton "Se connecter"
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs saisies dans les champs de texte
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Créer une instance de la classe SQLiteOpenHelper pour accéder à la base de données
                MaBaseDeDonneesHelper dbHelper = new MaBaseDeDonneesHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                // Effectuer une requête pour vérifier si les informations de connexion sont valides
                String query = "SELECT * FROM utilisateurs WHERE login = ? AND password = ?";
                Cursor cursor = db.rawQuery(query, new String[]{login, password});

                // Vérifier si le curseur contient des résultats
                if (cursor.moveToFirst()) {
                    // Afficher une boîte de dialogue avec une barre de progression pendant 2 secondes
                    ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setTitle("Connexion en cours");
                    progressDialog.setMessage("Veuillez patienter...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    // Stocker le login dans les SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", login);
                    editor.apply();

                    // Créer un Handler pour exécuter le code après un délai de 2 secondes
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Fermer la boîte de dialogue
                            progressDialog.dismiss();

                            // Rediriger vers MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("login", login);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000); // Délai de 2 secondes
                } else {
                    // Les informations de connexion sont invalides, afficher un message d'erreur
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Erreur");
                    builder.setMessage("Identifiants invalides!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    //Toast.makeText(getApplicationContext(), "Identifiants invalides", Toast.LENGTH_SHORT).show();
                }

                // Fermer le curseur et la connexion à la base de données
                cursor.close();
                db.close();
            }
        });
        /*
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs saisies dans les champs de texte
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Créer une instance de la classe SQLiteOpenHelper pour accéder à la base de données
                MaBaseDeDonneesHelper dbHelper = new MaBaseDeDonneesHelper(getApplicationContext());
                SQLiteDatabase db = dbHelper.getReadableDatabase();

                // Effectuer une requête pour vérifier si les informations de connexion sont valides
                String query = "SELECT * FROM utilisateurs WHERE login = ? AND password = ?";
                Cursor cursor = db.rawQuery(query, new String[]{login, password});

                // Vérifier si le curseur contient des résultats
                if (cursor.moveToFirst()) {
                    // Les informations de connexion sont valides, rediriger vers MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Les informations de connexion sont invalides, afficher un message d'erreur
                    Toast.makeText(getApplicationContext(), "Identifiants invalides", Toast.LENGTH_SHORT).show();
                }

                // Fermer le curseur et la connexion à la base de données
                cursor.close();
                db.close();
            }
        });
         */

    }
}