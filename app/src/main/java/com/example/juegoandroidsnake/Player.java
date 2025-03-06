package com.example.juegoandroidsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player {
    private Bitmap spriteSheet;
    private Bitmap[] frames;

    private int frameIndex = 0;  // Índice del frame actual
    private int frameCount = 8;  // Número total de frames
    private int frameWidth, frameHeight;  // Tamaño de cada frame
    private long lastFrameTime = 0;  // Tiempo del último cambio de frame
    private final int FRAME_DELAY = 100; // Milisegundos entre frames


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

    private Rect detectCollision;

    public Player(Context context, int screenX, int screenY) {
        x = 50; // Comienza en el borde izquierdo

        spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_sprite);
        spriteSheet = Bitmap.createScaledBitmap(spriteSheet, 864, 130, false);

        frameWidth = spriteSheet.getWidth() / frameCount; // Dividir la imagen en frames
        frameHeight = spriteSheet.getHeight();

        frames = new Bitmap[frameCount];
        for (int i = 0; i < frameCount; i++) {
            Bitmap frame = Bitmap.createBitmap(spriteSheet, i * frameWidth, 0, frameWidth, frameHeight);

            frames[i] = Bitmap.createScaledBitmap(frame, frameWidth * 2, frameHeight * 2, false); // Doble tamaño
        }
        y = screenY - frameHeight - 200; // Coloca al jugador sobre el suelo

        maxX = screenX;
        maxY = screenY - frameHeight -280; // Ajuste de altura del jugador
        minY = 0;

        detectCollision = new Rect(x, y, x + frameWidth, y + frameHeight);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(frames[frameIndex], x, y, null);
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

        // Si llega al borde derecho, reaparece en la izquierda
        if (x > maxX - frameWidth) {
            x = 0;
        }


        // Aplicar gravedad
        velocidadY += GRAVEDAD;
        y += velocidadY;

// Evitar que el personaje atraviese el suelo
        if (y > maxY) {
            y = maxY;
            velocidadY = 0;
            saltosRealizados = 0; // Restablecer los saltos al tocar el suelo
        }


        // Controlar la animación (cambiar frame cada FRAME_DELAY)
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime > FRAME_DELAY) {
            frameIndex = (frameIndex + 1) % frameCount;
            lastFrameTime = currentTime;
        }

        // Actualizar hitbox de colisión
        detectCollision.set(x, y, x + frameWidth, y + frameHeight);
    }

    public void moverDerecha() {
        x += VELOCIDAD;
        if (x > maxX - frameWidth) {
            x = maxX - frameWidth; // Evita que salga de la pantalla
        }
    }

    public Bitmap getCurrentFrame() {
        return frames[frameIndex]; // Retorna el frame animado actual
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public float getSpeed() {
        return velocidadY;
    }
}
