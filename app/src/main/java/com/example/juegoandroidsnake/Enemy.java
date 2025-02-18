package com.example.juegoandroidsnake;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Enemy {
    private Bitmap bitmap;

    private int x;
    private int y;

    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;

    public Enemy(Context context, int screenX, int screenY){
        bitmap = BitmapFactory.decodeResource(R.drawable._8c73c12c932443aec56fea55bcc704b)
    }

}
