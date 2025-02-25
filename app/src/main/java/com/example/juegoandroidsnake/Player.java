package com.example.juegoandroidsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Player {
    private Bitmap bitmap;
    private int x;
    private int y;
    private float velocidadY = 0; // Velocidad en el eje Y (float para mayor precisión)
    private final float GRAVEDAD = 0.5f;
    private final int FUERZA_SALTO = -12; // Fuerza del salto
    private final int MAX_SALTOS = 2; // Máximo de saltos permitidos
    private int saltosRealizados = 0; // Contador de saltos

    private int maxY; // Altura máxima
    private int minY;
    private int sueloY; // Posición del suelo

    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY) {
        x = 750;
        y = screenY - 100; // Inicia sobre el suelo
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);

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

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void moverArriba() {
        y -= 20;
        if (y < minY) y = minY;
    }

    public void moverAbajo() {
        y += 20;
        if (y > sueloY) y = sueloY;
    }

    public void moverIzquierda() {
        x -= 20;
        if (x < 0) x = 0;
    }

    public void moverDerecha() {
        x += 20;
        if (x > maxY - bitmap.getWidth()) x = maxY - bitmap.getWidth();
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
