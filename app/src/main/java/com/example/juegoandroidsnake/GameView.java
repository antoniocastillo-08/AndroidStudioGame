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

    private Bitmap background; // Imagen de fondo

    private Bitmap upArrow, downArrow, leftArrow, rightArrow;
    private int dpadSize = 200; // Tamaño de cada botón de la cruceta
    private int dpadX, dpadY;


    public GameView(Context context, int screenX, int screenY) {
        super(context);

        // Inicializar el jugador
        player = new Player(context, screenX, screenY);

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

        upArrow = BitmapFactory.decodeResource(getResources(), R.drawable.top);
        downArrow = BitmapFactory.decodeResource(getResources(), R.drawable.down);
        leftArrow = BitmapFactory.decodeResource(getResources(), R.drawable.left);
        rightArrow = BitmapFactory.decodeResource(getResources(), R.drawable.right);

// Posición en la esquina inferior izquierda
        dpadX = 500;
        dpadY = 100 ;

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

            // Dibujar el suelo
            paint.setColor(Color.rgb(13, 60, 161));
            canvas.drawRect(0, getHeight() - 150, getWidth(), getHeight(), paint);

            // Dibujar el jugador
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);

            // Dibujar la cruceta
            canvas.drawBitmap(upArrow, dpadX + dpadSize, dpadY, paint);
            canvas.drawBitmap(downArrow, dpadX + dpadSize, dpadY + dpadSize * 2, paint);
            canvas.drawBitmap(leftArrow, dpadX, dpadY + dpadSize, paint);
            canvas.drawBitmap(rightArrow, dpadX + dpadSize * 2, dpadY + dpadSize, paint);

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
