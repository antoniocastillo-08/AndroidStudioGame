package com.example.juegoandroidsnake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AdView adView;
    private ImageButton buttonPlay; // Botón para iniciar el juego
    private ImageButton buttonExit; // Botón para salir de la aplicación

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_view);

        // Inicializar AdMob
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                // AdMob se ha inicializado correctamente
            }
        });

        // Configurar el AdView
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Configurar los botones
        buttonPlay = findViewById(R.id.imageButton1); // Botón de inicio
        buttonExit = findViewById(R.id.imageButton2); // Botón de salida

        // Asignar el listener a los botones
        buttonPlay.setOnClickListener(this);
        buttonExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imageButton1) {
            // Iniciar la actividad del juego (GameActivity)
            startActivity(new Intent(this, GameActivity.class));
        } else if (view.getId() == R.id.imageButton2) {
            // Salir de la aplicación
            finishAffinity(); // Cierra la actividad actual y todas las actividades padre
            System.exit(0); // Cierra la aplicación completamente
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (adView != null) {
            adView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adView != null) {
            adView.destroy();
        }
    }
}