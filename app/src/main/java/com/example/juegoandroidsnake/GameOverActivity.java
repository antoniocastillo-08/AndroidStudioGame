package com.example.juegoandroidsnake;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayerGanar; // Reproductor de música de victoria

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_view);

        // Obtener el contador de muertes del Intent
        int muertes = getIntent().getIntExtra("muertes", 0);

        // Referencias a los componentes del layout
        TextView textViewMuertes = findViewById(R.id.textViewMuertes);
        Button buttonReiniciar = findViewById(R.id.buttonReiniciar);
        Button buttonMenu = findViewById(R.id.buttonMenu);

        // Mostrar la cantidad de muertes
        textViewMuertes.setText("Muertes: " + muertes);

        // Configurar el botón de reiniciar
        buttonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarJuego(); // Llamar al método para reiniciar el juego
            }
        });

        // Configurar el botón de menú principal
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volverAlMenu(); // Llamar al método para volver al menú principal
            }
        });
    }

    private void reiniciarJuego() {
        // Detener la música de victoria si está reproduciéndose
        if (mediaPlayerGanar != null && mediaPlayerGanar.isPlaying()) {
            mediaPlayerGanar.stop();
            mediaPlayerGanar.release();
            mediaPlayerGanar = null;
        }

        // Reiniciar el juego
        Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
        startActivity(intent);
        finish(); // Cerrar esta actividad
    }

    private void volverAlMenu() {
        // Detener la música de victoria si está reproduciéndose
        if (mediaPlayerGanar != null && mediaPlayerGanar.isPlaying()) {
            mediaPlayerGanar.stop();
            mediaPlayerGanar.release();
            mediaPlayerGanar = null;
        }

        // Volver al menú principal
        Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Cerrar esta actividad
    }
}