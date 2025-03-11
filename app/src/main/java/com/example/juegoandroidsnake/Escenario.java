package com.example.juegoandroidsnake;

import android.content.Context;
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

        // Generar plataformas según el tipo de escenario
        generarPlataformas(tipoEscenario);
    }

    // Método para generar plataformas basadas en un tipo específico de escenario
    private void generarPlataformas(int tipoEscenario) {
        // Limpiar las plataformas existentes
        plataformas.clear();
        plataformasMuerte.clear();
        plataformaMeta = null; // Reiniciar la plataforma de meta

        // Generar plataformas según el tipo de escenario
        switch (tipoEscenario) {
            case 1:
                plataformas.add(new Rect(0, screenY - 300, 600, screenY));
                plataformasMuerte.add(new Rect(700, screenY - 300, 900, screenY - 250));
                plataformas.add(new Rect(1000, screenY - 400, 1500, screenY));
                plataformas.add(new Rect(1700, screenY - 450, 1900, screenY - 400));
                break;
            case 2:
                plataformas.add(new Rect(0, screenY - 300, 600, screenY));
                plataformas.add(new Rect(600, screenY - 400, 1200, screenY));
                plataformas.add(new Rect(1200, screenY - 500, 1700, screenY));
                break;
            case 3:
                plataformas.add(new Rect(100, screenY - 350, 400, screenY - 300));
                plataformas.add(new Rect(700, screenY - 350, 1100, screenY - 300));
                plataformasMuerte.add(new Rect(1300, screenY - 350, 1600, screenY - 300));
                plataformas.add(new Rect(1800, screenY - 350, 2100, screenY - 300));
                break;
            case 4:
                plataformas.add(new Rect(0, screenY - 350, 500, screenY));
                plataformas.add(new Rect(800, screenY - 425, 1100, screenY));
                plataformasMuerte.add(new Rect(1300, screenY - 450, 1500, screenY-400));
                plataformas.add(new Rect(1700, screenY - 500, screenX, screenY));
                break;
            case 5:
                plataformas.add(new Rect(0, screenY - 400, 400, screenY - 350));
                plataformas.add(new Rect(400, screenY - 450, 700, screenY - 400));
                plataformas.add(new Rect(700, screenY - 500, 1000, screenY - 450));
                plataformas.add(new Rect(1000, screenY - 550, 1300, screenY - 500));
                plataformas.add(new Rect(1300, screenY - 600, 1600, screenY - 550));

                plataformaMeta = new Rect(screenX - 400, screenY - 400, screenX, screenY);
                break;
        }
    }

    public void cambiarEscenario() {
        tipoEscenarioActual = (tipoEscenarioActual % 13) + 1; // Cambiar entre 1 y 13
        generarPlataformas(tipoEscenarioActual); // Generar nuevas plataformas
    }

    public void reiniciarEscenario() {
        tipoEscenarioActual = 1; // Reiniciar al tipo 1
        generarPlataformas(tipoEscenarioActual); // Generar nuevas plataformas
    }

    public void dibujar(Canvas canvas) {
        // Dibujar el suelo
        paint.setColor(Color.rgb(13, 60, 161)); // Color del suelo
        canvas.drawRect(suelo, paint);

        // Dibujar las plataformas
        paint.setColor(Color.GREEN); // Color de las plataformas
        for (Rect plataforma : plataformas) {
            canvas.drawRect(plataforma, paint);
        }
        paint.setColor(Color.RED); // Color de las plataformas de muerte
        for (Rect plataformaMuerte : plataformasMuerte) {
            canvas.drawRect(plataformaMuerte, paint);
        }
        // Dibujar la plataforma de meta si existe
        if (plataformaMeta != null) {
            paint.setColor(Color.YELLOW); // Color de la plataforma de meta
            canvas.drawRect(plataformaMeta, paint);
        }
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