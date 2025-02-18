package com.example.juegoandroidsnake;

import java.util.Random;

public class Jungle {
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;

    public Jungle(int screenX, int screenY) {
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = generator.nextInt(10);

        //generating a random coordinate
        //but keeping the coordinate inside the screen size
        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }

    public void update(int playerSpeed){
        x -= playerSpeed;
        x -= speed;
        if(x < 0){
            x= maxX;
            Random generator = new Random();
            y=generator.nextInt(maxY);
            speed= generator.nextInt(15);
        }
    }
    public  float getJungleWidth(){
        float minX= 1.0f;
        float maxX = 4.0f;
        Random rand = new Random();
        float finalX = rand.nextFloat() * (maxX - minX) + minX;
        return finalX;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
