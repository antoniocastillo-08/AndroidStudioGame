package com.example.juegoandroidsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Belal on 6/15/2016.
 */
public class Enemy {
    private Bitmap bitmap;
    private Bitmap upArrow, downArrow, leftArrow, rightArrow;
    private int dpadSize = 200; // Tamaño de cada botón de la cruceta
    private int dpadX, dpadY;

    private int x;
    private int y;

    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    private Rect detectCollision;

    public Enemy(Context context, int screenX, int screenY) {
        //getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);

        //initializing min and max coordinates
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        //generating a random coordinate to add enemy
        Random generator = new Random();
        speed = generator.nextInt(6) + 10;
        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight();

        detectCollision = new Rect(x,y,bitmap.getWidth(), bitmap.getHeight());

    }
    public void update(int playerSpeed){
        x -= speed;
        if (x < minX-bitmap.getWidth() ){
            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            x= maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }

        detectCollision.left= x;
        detectCollision.top= y;
        detectCollision.right= x + bitmap.getWidth();
        detectCollision.bottom= y + bitmap.getHeight();

    }
    public void setX(int x){
        this.x = x;
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

    public int getSpeed() {
        return speed;
    }
}