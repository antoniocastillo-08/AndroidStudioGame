package com.example.juegoandroidsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player {
    private Bitmap bitmap;
    private int x;
    private int y;
    private final int VELOCIDAD = 10;
    private float velocidadY = 0; // Velocidad en el eje Y (float para mayor precisión)
    private final float GRAVEDAD = 0.5f;
    private final int FUERZA_SALTO = -12; // Fuerza del salto
    private final int MAX_SALTOS = 2; // Máximo de saltos permitidos
    private int saltosRealizados = 0; // Contador de saltos

    private int maxX; // Borde derecho
    private int maxY; // Altura máxima
    private int minY;
    private int sueloY; // Posición del suelo

    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY) {
        x = 50; // Comienza en el borde izquierdo
        y = screenY - 100; // Inicia sobre el suelo
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

        maxX = screenX;
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        sueloY = maxY - 150; // Suelo en la parte baja de la pantalla

        detectCollision = new Rect(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
    }

    public void saltar() {
        if (saltosRealizados < MAX_SALTOS) {
            velocidadY = FUERZA_SALTO; // Aplicar fuerza de salto
            saltosRealizados++; // Incrementar contador de saltos
        }
    }

    public void update() {
        // Movimiento automático hacia la derecha
        x += VELOCIDAD;

        // Si llega al borde derecho, reaparece en la izquierda (opcional)
        if (x > maxX - bitmap.getWidth()) {
            x = 0; // Reinicia desde la izquierda
        }

        // Aplicar gravedad de forma acumulativa
        velocidadY += GRAVEDAD;
        y += velocidadY;

        // Límite inferior (suelo)
        if (y > sueloY) {
            y = sueloY;
            velocidadY = 0; // Detener movimiento
            saltosRealizados = 0; // Resetear saltos al tocar el suelo
        }

        // Límite superior (evita que el jugador salga de la pantalla)
        if (y < minY) {
            y = minY;
            velocidadY = 0;
        }

        // Actualizar hitbox de colisión
        detectCollision.set(x, y, x + bitmap.getWidth(), y + bitmap.getHeight());
    }

    public void moverDerecha() {
        x += VELOCIDAD;
        if (x > maxX - bitmap.getWidth()) {
            x = maxX - bitmap.getWidth(); // Evita que salga de la pantalla
        }
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public float getSpeed() {
        return velocidadY;
    }
}
