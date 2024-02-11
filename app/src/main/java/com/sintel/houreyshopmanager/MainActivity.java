package com.sintel.houreyshopmanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sintel.houreyshopmanager.GestionProduits.AddProduitsActivity;
import com.sintel.houreyshopmanager.GestionProduits.ListeProduitsActivity;
import com.sintel.houreyshopmanager.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String login = getIntent().getStringExtra("login");
        TextView loginText= findViewById(R.id.UserLogin);

        loginText.setText(login);

        CardView gestionProduitCard = findViewById(R.id.GestionProduit);
        gestionProduitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code pour afficher la boîte de dialogue personnalisée

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.options_menu, null);
                builder.setView(view);

                LinearLayout ajoutProduitsLayout = view.findViewById(R.id.AjoutProduits);
                LinearLayout listeProduitsLayout = view.findViewById(R.id.ListeProduits);
                TextView btnExit = view.findViewById(R.id.btn_exit);

                final AlertDialog dialog = builder.create(); // Déclarer la variable dialog ici

                ajoutProduitsLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Action à effectuer lorsque le LinearLayout AjoutProduits est cliqué
                        // Ajoutez votre code ici
                        startActivity(new Intent(MainActivity.this, AddProduitsActivity.class));
                        dialog.dismiss();
                    }
                });

                listeProduitsLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Action à effectuer lorsque le LinearLayout ListeProduits est cliqué
                        startActivity(new Intent(MainActivity.this, ListeProduitsActivity.class));
                        dialog.dismiss();
                    }
                });

                btnExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Fermer la boîte de dialogue
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }


}