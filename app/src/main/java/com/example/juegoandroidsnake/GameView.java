package com.example.juegoandroidsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

    private Enemy[] enemies;
    private int enemyCount = 3;
    private Boom boom;
    private int sueloY;

    private Bitmap background; // Imagen de fondo

    private Escenario escenario;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        // Inicializar el jugador
        player = new Player(context, screenX, screenY);
        int sueloY = screenY - 200;

        escenario = new Escenario(screenX,screenY,1);
        // Inicializar objetos de dibujo
        surfaceHolder = getHolder();
        paint = new Paint();


        background = BitmapFactory.decodeResource(getResources(), R.drawable.fondo);
        background = Bitmap.createScaledBitmap(background, screenX, screenY, false);

        // Crear enemigos
        enemies = new Enemy[enemyCount];
        for (int i = 0; i < enemyCount; i++) {
            enemies[i] = new Enemy(context, screenX, screenY);
        }
        boom = new Boom(context);


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
        player.moverDerecha();
        player.update();

    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            // Dibujar el fondo
            canvas.drawBitmap(background, 0, 0, paint);

            escenario.dibujar(canvas);


            // Dibujar el jugador
            canvas.drawBitmap(player.getCurrentFrame(),player.getX(),player.getY(),paint);
            // Dibujar enemigos
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(enemies[i].getBitmap(), enemies[i].getX(), enemies[i].getY(), paint);
            }

            // Dibujar explosión si hay colisión
            canvas.drawBitmap(boom.getBitmap(), boom.getX(), boom.getY(), paint);

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
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            player.saltar(); // Solo salta al tocar la pantalla
        }
        return true;
    }



}
