package com.sintel.houreyshopmanager.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.sintel.houreyshopmanager.MainActivity;
import com.sintel.houreyshopmanager.R;
import com.sintel.houreyshopmanager.login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int SPLASH_TIME_OUT = 1000;
    private static final int PROGRESS_UPDATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progressBar);

        simulateProgress();

/*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
 */


    }

    private void simulateProgress() {
        final int totalProgressTime = 5000; // 5 secondes
        final int incrementTime = 100; // Mettez à jour la barre de progression toutes les 100 millisecondes
        final int progressIncrement = 100 / (totalProgressTime / incrementTime);

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if (message.what == PROGRESS_UPDATE) {
                    if (progressBar.getProgress() < 100) {
                        progressBar.setProgress(progressBar.getProgress() + progressIncrement);
                    } else {
                      //  progressBar.setVisibility(View.INVISIBLE);
                    }
                }
                return true;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = 0;

                while (progress < 100) {
                    try {
                        Thread.sleep(incrementTime);
                        progress += progressIncrement;

                        Message message = new Message();
                        message.what = PROGRESS_UPDATE;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);
                // Démarrer MainActivity2 (remplacez MainActivity2 par le nom de votre prochaine activité)
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                // Terminez l'activité actuelle si vous ne voulez pas revenir à cette activité en appuyant sur le bouton Retour
                finish();
            }
        }).start();
    }
}