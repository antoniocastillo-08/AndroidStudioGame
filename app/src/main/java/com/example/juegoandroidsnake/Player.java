package com.example.juegoandroidsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

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
    private final int VELOCIDAD = 20;
    private float velocidadY = 0; // Velocidad en el eje Y (float para mayor precisión)
    private final float GRAVEDAD = 0.5f;
    private final int FUERZA_SALTO = -12; // Fuerza del salto
    private final int MAX_SALTOS = 2; // Máximo de saltos permitidos
    private int saltosRealizados = 0; // Contador de saltos
    private boolean gameOver; // Estado de Game Over

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

        // Escalar los frames al doble de su tamaño original
        frames = new Bitmap[frameCount];
        for (int i = 0; i < frameCount; i++) {
            Bitmap frame = Bitmap.createBitmap(spriteSheet, i * frameWidth, 0, frameWidth, frameHeight);
            frames[i] = Bitmap.createScaledBitmap(frame, frameWidth * 2, frameHeight * 2, false); // Doble tamaño
        }

        // Ajustar el tamaño del frameWidth y frameHeight después de escalar
        frameWidth = frames[0].getWidth(); // Nuevo ancho después de escalar
        frameHeight = frames[0].getHeight(); // Nuevo alto después de escalar

        // Posición inicial del jugador
        y = screenY - frameHeight - 200; // Coloca al jugador sobre el suelo

        // Límites de la pantalla
        maxX = screenX;
        maxY = screenY - frameHeight; // Ajuste de altura del jugador
        minY = 0;

        // Inicializar el Rect de colisión con el tamaño del sprite
        detectCollision = new Rect(x, y, x + frameWidth, y + frameHeight);
        gameOver = false; // Inicialmente, el juego no está en estado de Game Over
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

    public void update(ArrayList<Rect> plataformas, Rect suelo) {
        if (gameOver) {
            return; // Si el juego está en estado de Game Over, no actualizar al jugador
        }

        // Movimiento automático hacia la derecha
        int nuevaX = x + VELOCIDAD;

        // Verificar colisión lateral con las plataformas
        boolean colisionLateral = false;
        Rect nuevaColision = new Rect(nuevaX, y, nuevaX + frameWidth, y + frameHeight);

        for (Rect plataforma : plataformas) {
            if (Rect.intersects(nuevaColision, plataforma)) {
                // Verificar si el jugador está encima de la plataforma
                boolean encimaDePlataforma = (y + frameHeight <= plataforma.top + 10); // Margen de error

                // Si no está encima de la plataforma, manejar colisión lateral
                if (!encimaDePlataforma) {
                    // Colisión lateral (izquierda o derecha)
                    if (nuevaX + frameWidth > plataforma.left && nuevaX < plataforma.left) {
                        // Colisión por la izquierda
                        nuevaX = plataforma.left - frameWidth;
                        x = nuevaX; // Detener al jugador a la izquierda de la plataforma
                        colisionLateral = true;
                        gameOver = true; // Activar estado de Game Over
                    }
                }
            }
        }

        // Si no hay colisión lateral, actualizar la posición horizontal
        if (!colisionLateral) {
            x = nuevaX;
        }

        // Si llega al borde derecho, reaparece en la izquierda
        if (x > maxX - frameWidth) {
            x = 0;
        }

        // Aplicar gravedad
        velocidadY += GRAVEDAD;
        y += velocidadY;

        // Verificar colisión con el suelo
        if (Rect.intersects(detectCollision, suelo)) {
            y = suelo.top - frameHeight; // Colocar al jugador sobre el suelo
            velocidadY = 0; // Detener la caída
            saltosRealizados = 0; // Restablecer los saltos
        }

        // Verificar colisión con las plataformas (vertical)
        boolean enPlataforma = false;
        for (Rect plataforma : plataformas) {
            if (Rect.intersects(detectCollision, plataforma) && velocidadY >= 0) {
                // Si el jugador está cayendo y colisiona con una plataforma, lo colocamos sobre la plataforma
                y = plataforma.top - frameHeight;
                velocidadY = 0; // Detener la caída
                saltosRealizados = 0; // Restablecer los saltos
                enPlataforma = true;
                break;
            }
        }

        // Si no está en una plataforma ni en el suelo, seguir cayendo
        if (!enPlataforma && !Rect.intersects(detectCollision, suelo)) {
            // No hacer nada, dejar que la gravedad actúe
        }

        // Controlar la animación (cambiar frame cada FRAME_DELAY)
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime > FRAME_DELAY) {
            frameIndex = (frameIndex + 1) % frameCount;
            lastFrameTime = currentTime;
        }

        // Actualizar hitbox de colisión con el tamaño del sprite
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

    public boolean isGameOver() {
        return gameOver; // Método para verificar si el juego está en estado de Game Over
    }

    public void reset() {
        gameOver = false; // Reiniciar el estado de Game Over
        x = 50; // Reiniciar la posición del jugador
        y = maxY - frameHeight - 200; // Reiniciar la posición del jugador
        velocidadY = 0; // Reiniciar la velocidad vertical
        saltosRealizados = 0; // Reiniciar el contador de saltos
        detectCollision.set(x, y, x + frameWidth, y + frameHeight); // Reiniciar el Rect de colisión
    }
}