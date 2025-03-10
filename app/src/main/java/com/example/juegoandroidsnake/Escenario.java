package com.example.juegoandroidsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.ArrayList;

public class Escenario {

    private ArrayList<Rect> plataformas; // Lista de plataformas
    private Rect suelo; // Suelo del escenario
    private Paint paint;
    private int tipoEscenarioActual;
    private int screenX;
    private int screenY;
    private ArrayList<Rect> plataformasMuerte;
    private Rect plataformaMeta; // Plataforma de meta final



    public Escenario(int screenX, int screenY, int tipoEscenario, Context context) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.tipoEscenarioActual = tipoEscenario;
        plataformasMuerte = new ArrayList<>();
        plataformas = new ArrayList<>();
        paint = new Paint();

        // Suelo
        suelo = new Rect(0, screenY - 200, screenX, screenY); // Ajuste de altura del suelo
        plataformaMeta = new Rect(screenX - 300, screenY - 400, screenX - 100, screenY - 350);

        // Generar plataformas según el tipo de escenario
        generarPlataformas(tipoEscenario);
    }

    // Método para generar plataformas basadas en un tipo específico de escenario
    private void generarPlataformas(int tipoEscenario) {
        // Tipo 1: Escenario simple
        if (tipoEscenario == 1) {
            plataformas.add(new Rect(0, screenY - 300, 600, screenY));
            plataformasMuerte.add(new Rect(700, screenY - 300, 900, screenY - 250));
            plataformas.add(new Rect(1000, screenY - 400, 1500, screenY));
            plataformas.add(new Rect(1700, screenY - 450, 1900, screenY - 400));
        }
        // Tipo 2: Escenario con muchas plataformas
        else if (tipoEscenario == 2) {
            plataformas.add(new Rect(0, screenY - 300, 600, screenY));
            plataformas.add(new Rect(600, screenY - 400, 1200, screenY));
            plataformas.add(new Rect(1200, screenY - 500, 1700, screenY));
        }
        // Tipo 3: Escenario de plataformas muy altas
        else if (tipoEscenario == 3) {
            plataformas.add(new Rect(100, screenY - 350, 400, screenY - 300));
            plataformas.add(new Rect(700, screenY - 350, 1000, screenY - 300));
            plataformas.add(new Rect(1300, screenY - 350, 1600, screenY - 300));
            plataformas.add(new Rect(1900, screenY - 350, 2100, screenY - 300));
        }
        // Tipo 4: Escenario con plataformas móviles
        else if (tipoEscenario == 4) {
            plataformas.add(new Rect(100, screenY - 400, 600, screenY - 350));
            plataformas.add(new Rect(700, screenY - 500, 1200, screenY - 450));
            plataformasMuerte.add(new Rect(1300, screenY - 300, 1500, screenY - 250));
            plataformas.add(new Rect(1600, screenY - 500, 2000, screenY - 450));
        }
        // Tipo 5: Escenario con plataformas que desaparecen
        else if (tipoEscenario == 5) {
            plataformas.add(new Rect(100, screenY - 400, 500, screenY - 350));
            plataformas.add(new Rect(700, screenY - 850, 1200, screenY - 800));
            plataformasMuerte.add(new Rect(1300, screenY - 300, 1500, screenY - 250));
            plataformas.add(new Rect(1600, screenY - 500, 2000, screenY - 450));
        }
        // Tipo 6: Escenario con plataformas estrechas
        else if (tipoEscenario == 6) {
            plataformas.add(new Rect(100, screenY - 400, 300, screenY - 350));
            plataformas.add(new Rect(500, screenY - 600, 700, screenY - 550));
            plataformasMuerte.add(new Rect(800, screenY - 300, 1000, screenY - 250));
            plataformas.add(new Rect(1200, screenY - 500, 1400, screenY - 450));
        }
        // Tipo 7: Escenario con plataformas en zigzag
        else if (tipoEscenario == 7) {
            plataformas.add(new Rect(100, screenY - 400, 600, screenY - 350));
            plataformas.add(new Rect(700, screenY - 600, 1200, screenY - 550));
            plataformasMuerte.add(new Rect(1300, screenY - 300, 1500, screenY - 250));
            plataformas.add(new Rect(1600, screenY - 500, 2000, screenY - 450));
        }
        // Tipo 8: Escenario con plataformas que se mueven verticalmente
        else if (tipoEscenario == 8) {
            plataformas.add(new Rect(100, screenY - 400, 600, screenY - 350));
            plataformas.add(new Rect(700, screenY - 600, 1200, screenY - 550));
            plataformasMuerte.add(new Rect(1300, screenY - 300, 1500, screenY - 250));
            plataformas.add(new Rect(1600, screenY - 500, 2000, screenY - 450));
        }
        // Tipo 9: Escenario con plataformas que se mueven horizontalmente
        else if (tipoEscenario == 9) {
            plataformas.add(new Rect(100, screenY - 400, 600, screenY - 350));
            plataformas.add(new Rect(700, screenY - 600, 1200, screenY - 550));
            plataformasMuerte.add(new Rect(1300, screenY - 300, 1500, screenY - 250));
            plataformas.add(new Rect(1600, screenY - 500, 2000, screenY - 450));
        }
        // Tipo 10: Escenario con plataformas que se mueven en círculos
        else if (tipoEscenario == 10) {
            plataformas.add(new Rect(100, screenY - 400, 600, screenY - 350));
            plataformas.add(new Rect(700, screenY - 600, 1200, screenY - 550));
            plataformasMuerte.add(new Rect(1300, screenY - 300, 1500, screenY - 250));
            plataformas.add(new Rect(1600, screenY - 500, 2000, screenY - 450));
        }
        // Tipo 11: Escenario con plataformas que se mueven en espiral
        else if (tipoEscenario == 11) {
            plataformas.add(new Rect(100, screenY - 400, 600, screenY - 350));
            plataformas.add(new Rect(700, screenY - 600, 1200, screenY - 550));
            plataformasMuerte.add(new Rect(1300, screenY - 300, 1500, screenY - 250));
            plataformas.add(new Rect(1600, screenY - 500, 2000, screenY - 450));
        }
        // Tipo 12: Escenario con plataformas que se mueven en forma de onda
        else if (tipoEscenario == 12) {
            plataformas.add(new Rect(100, screenY - 400, 600, screenY - 350));
            plataformas.add(new Rect(700, screenY - 600, 1200, screenY - 550));
            plataformasMuerte.add(new Rect(1300, screenY - 300, 1500, screenY - 250));
            plataformas.add(new Rect(1600, screenY - 500, 2000, screenY - 450));
        }
        // Tipo 13: Escenario con plataformas que se mueven en forma de estrella
        else if (tipoEscenario == 13) {
            plataformas.add(new Rect(100, screenY - 400, 600, screenY - 350));
            plataformas.add(new Rect(700, screenY - 600, 1200, screenY - 550));
            plataformasMuerte.add(new Rect(1300, screenY - 300, 1500, screenY - 250));
            plataformas.add(new Rect(1600, screenY - 500, 2000, screenY - 450));
        }
    }

    public void cambiarEscenario() {
        tipoEscenarioActual = (tipoEscenarioActual % 5) + 1; // Cambiar entre 1 y 5
        plataformas.clear(); // Limpiar las plataformas actuales
        plataformasMuerte.clear();
        generarPlataformas(tipoEscenarioActual); // Generar nuevas plataformas

    }

    public void reiniciarEscenario() {
        tipoEscenarioActual = 1; // Reiniciar al tipo 1
        plataformas.clear();
        plataformasMuerte.clear();// Limpiar las plataformas actuales
        generarPlataformas(tipoEscenarioActual); // Generar nuevas plataformas
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
        paint.setColor(Color.RED); // Color de las plataformas de muerte
        for (Rect plataformaMuerte : plataformasMuerte) {
            canvas.drawRect(plataformaMuerte, paint);
        }
        paint.setColor(Color.YELLOW); // Color de la plataforma de meta
        canvas.drawRect(plataformaMeta, paint);

    }

    public ArrayList<Rect> getPlataformas() {
        return plataformas;
    }

    public ArrayList<Rect> getPlataformasMuerte() {
        return plataformasMuerte;
    }
    public Rect getPlataformaMeta() {
        return plataformaMeta;
    }

    public Rect getSuelo() {
        return suelo;
    }
}