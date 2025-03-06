package com.example.juegoandroidsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    volatile boolean playing;
    private Thread gameThread = null;
    private Player player;

    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private SoundPool soundPool;
    private int saltoSoundId;

    private Enemy[] enemies;
    private int enemyCount = 3;
    private Boom boom;
    private int sueloY;

    private Bitmap background; // Imagen de fondo
    private Bitmap gameOverImage; // Imagen de Game Over

    private Escenario escenario;
    private boolean gameOver; // Estado de Game Over
    private int contadorMuertes; // Contador de muertes

    private MediaPlayer mediaPlayer; // Reproductor de música de fondo

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        // Inicializar el jugador
        player = new Player(context, screenX, screenY);
        sueloY = screenY - 200;

        escenario = new Escenario(screenX, screenY, 1);

        // Inicializar objetos de dibujo
        surfaceHolder = getHolder();
        paint = new Paint();

        // Cargar la imagen de fondo
        background = BitmapFactory.decodeResource(getResources(), R.drawable.fondo);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

        // Cargar la imagen de Game Over
        gameOverImage = BitmapFactory.decodeResource(getResources(), R.drawable.game_over);
        gameOverImage = Bitmap.createScaledBitmap(gameOverImage, screenX, screenY, false);

        // Crear enemigos
        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        // Inicializar la explosión
        boom = new Boom(context);

        // Inicializar estado de Game Over y contador de muertes
        gameOver = false;
        contadorMuertes = 0;

        soundPool = new SoundPool.Builder().setMaxStreams(5).build();
        saltoSoundId = soundPool.load(context, R.raw.salto, 1);

        // Inicializar el reproductor de música de fondo
        mediaPlayer = MediaPlayer.create(context, R.raw.musica_fondo);
        mediaPlayer.setLooping(true); // Repetir la música en bucle
        mediaPlayer.start(); // Iniciar la reproducción
    }

    @Override
    public void run() {
        while (playing) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        if (gameOver) {
            return; // Si el juego está en estado de Game Over, no actualizar
        }

        // Actualizar la posición del jugador y otros objetos
        player.update(escenario.getPlataformas(), escenario.getSuelo());

        // Verificar si el jugador ha activado el estado de Game Over
        if (player.isGameOver()) {
            gameOver = true; // Activar estado de Game Over
            contadorMuertes++; // Incrementar el contador de muertes
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Dibujar el fondo
            canvas.drawBitmap(background, 0, 0, paint);

            // Dibujar el escenario
            escenario.dibujar(canvas);

            // Dibujar el jugador
            canvas.drawBitmap(player.getCurrentFrame(), player.getX(), player.getY(), paint);

            // Dibujar enemigos
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(enemies[i].getBitmap(), enemies[i].getX(), enemies[i].getY(), paint);
            }

            // Dibujar explosión si hay colisión
            canvas.drawBitmap(boom.getBitmap(), boom.getX(), boom.getY(), paint);

            // Dibujar pantalla de Game Over si el juego ha terminado
            if (gameOver) {
                canvas.drawBitmap(gameOverImage, 0, 0, paint); // Dibujar la imagen de Game Over
            }

            // Dibujar el contador de muertes en la esquina superior izquierda
            paint.setColor(Color.WHITE); // Color del texto
            paint.setTextSize(50); // Tamaño del texto
            canvas.drawText("Muertes: " + contadorMuertes, 20, 50, paint); // Posición del texto

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
        }

        // Pausar la música de fondo cuando el juego se pausa
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();

        // Reanudar la música de fondo cuando el juego se reanuda
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            if (gameOver) {
                // Reiniciar el juego si está en estado de Game Over
                gameOver = false;
                player.reset();
            } else {
                // Lógica normal del juego (saltar, etc.)
                player.saltar();
                soundPool.play(saltoSoundId, 1.0f, 1.0f, 1, 0, 1.0f);

            }
        }
        return true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        // Liberar el MediaPlayer cuando la vista se destruye
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}