package com.example.juegoandroidsnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Random;

public class Escenario {

    private ArrayList<Rect> plataformas; // Lista de plataformas
    private Rect suelo; // Suelo del escenario
    private Paint paint;
    private Random random;

    private int screenX;
    private int screenY;
    private int maxPlataformas = 5; // Número máximo de plataformas

    public Escenario(int screenX, int screenY) {
        this.screenX = screenX;
        this.screenY = screenY;
        plataformas = new ArrayList<>();
        paint = new Paint();
        random = new Random();

        // Suelo
        suelo = new Rect(0, screenY - 150, screenX, screenY); // Ajuste de altura del suelo

        // Generar plataformas proceduralmente
        generarPlataformas();
    }

    // Método para generar plataformas de forma procedural
    private void generarPlataformas() {
        int minWidth = 200; // Ancho mínimo de plataforma
        int maxWidth = 500; // Ancho máximo de plataforma
        int minHeight = 100; // Distancia mínima entre plataformas
        int maxHeight = 400; // Distancia máxima entre plataformas

        for (int i = 0; i < maxPlataformas; i++) {
            int width = random.nextInt(maxWidth - minWidth) + minWidth; // Ancho aleatorio
            int height = random.nextInt(maxHeight - minHeight) + minHeight; // Altura aleatoria

            int x = random.nextInt(screenX - width); // Posición horizontal aleatoria
            int y = screenY - height - random.nextInt(maxHeight / 2); // Posición vertical aleatoria (más cerca del suelo)

            // Añadir la plataforma generada
            plataformas.add(new Rect(x, y, x + width, y + height));
        }
    }

    public void dibujar(Canvas canvas) {
        // Dibujar el suelo
        paint.setColor(Color.rgb(13, 60, 161)); // Color del suelo
        canvas.drawRect(suelo, paint);

        // Dibujar las plataformas
        paint.setColor(Color.rgb(0, 128, 0)); // Color de las plataformas
        for (Rect plataforma : plataformas) {
            canvas.drawRect(plataforma, paint);
        }
    }

    public ArrayList<Rect> getPlataformas() {
        return plataformas;
    }

    public Rect getSuelo() {
        return suelo;
    }

    // Método para verificar colisiones entre el jugador y las plataformas
    public boolean colisionConPlataforma(Rect jugador) {
        for (Rect plataforma : plataformas) {
            if (Rect.intersects(jugador, plataforma)) {
                return true; // Colisión detectada
            }
        }
        return false; // No hay colisión
    }
}
