package com.example.juegoandroidsnake;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;

public class Escenario {

    private ArrayList<Rect> plataformas; // Lista de plataformas
    private Rect suelo; // Suelo del escenario
    private Paint paint;

    private int screenX;
    private int screenY;

    public Escenario(int screenX, int screenY, int tipoEscenario) {
        this.screenX = screenX;
        this.screenY = screenY;
        plataformas = new ArrayList<>();
        paint = new Paint();

        // Suelo
        suelo = new Rect(0, screenY - 200, screenX, screenY); // Ajuste de altura del suelo

        // Generar plataformas según el tipo de escenario
        generarPlataformas(tipoEscenario);
    }

    // Método para generar plataformas basadas en un tipo específico de escenario
    private void generarPlataformas(int tipoEscenario) {
        // Tipo 1: Escenario simple
        if (tipoEscenario == 1) {
            plataformas.add(new Rect(0, screenY - 300, 600, screenY));
            plataformas.add(new Rect(1000, screenY - 400, 1500, screenY));

        }
        // Tipo 2: Escenario con muchas plataformas
        else if (tipoEscenario == 2) {
            plataformas.add(new Rect(100, screenY - 700, 300, screenY - 300));
            plataformas.add(new Rect(400, screenY - 500, 600, screenY - 450));
            plataformas.add(new Rect(700, screenY - 650, 900, screenY - 600));
            plataformas.add(new Rect(100, screenY - 800, 400, screenY - 750));
        }
        // Tipo 3: Escenario de plataformas muy altas
        else if (tipoEscenario == 3) {
            plataformas.add(new Rect(100, screenY - 500, 300, screenY - 450));
            plataformas.add(new Rect(400, screenY - 800, 600, screenY - 750));
            plataformas.add(new Rect(800, screenY - 1000, 1000, screenY - 950));
        }
        // Tipo 4: Escenario con plataformas grandes y separadas
        else if (tipoEscenario == 4) {
            plataformas.add(new Rect(100, screenY - 400, 600, screenY - 350));
            plataformas.add(new Rect(700, screenY - 650, 1200, screenY - 600));
        }
        // Tipo 5: Escenario con plataformas en el aire
        else if (tipoEscenario == 5) {
            plataformas.add(new Rect(100, screenY - 600, 500, screenY - 550));
            plataformas.add(new Rect(700, screenY - 850, 1200, screenY - 800));
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
}