package com.example.juegoandroidsnake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    private TextView textViewMuertes;
    private Button buttonReiniciar;
    private Button buttonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_view);

        // Obtener el contador de muertes del Intent
        int muertes = getIntent().getIntExtra("muertes", 0);

        // Referencias a los componentes del layout
        textViewMuertes = findViewById(R.id.textViewMuertes);
        buttonReiniciar = findViewById(R.id.buttonReiniciar);
        buttonMenu = findViewById(R.id.buttonMenu);

        // Mostrar la cantidad de muertes
        textViewMuertes.setText("Muertes: " + muertes);

        // Configurar el botón de reiniciar
        buttonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reiniciar el juego
                Intent intent = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(intent);
                finish(); // Cerrar esta actividad
            }
        });

        // Configurar el botón de menú principal
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver al menú principal
                Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cerrar esta actividad
            }
        });
    }
}