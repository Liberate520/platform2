package ru.samsung.platformer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {

    private List<Rect> frames = new ArrayList<>();
    private int currentFrame = 0;
    private int timeForCurrentFrame = 0;
    private int frameTime = 80;
    private boolean stay = false;
    private double maxSpeed = 10;

    public Player(float x, float y, Bitmap bitmap, int countFrame) {
        super(x, y, bitmap);
        width = bitmap.getWidth() / countFrame; //в изображении 6 кадров
        height = bitmap.getHeight();
        for (int i = 0; i < 6; i++) {
            frames.add(new Rect(i * width, 0, i * width + width, height));
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        Rect position = new Rect((int) x, (int) y,
                (int) x + width, (int) y + height);
        canvas.drawBitmap(bitmap, frames.get(currentFrame), position, paint);
    }

    public void update(int ms, double actX, double actY) {

        timeForCurrentFrame += ms;
        if (timeForCurrentFrame >= frameTime) {
            currentFrame = (currentFrame + 1) % frames.size();
            timeForCurrentFrame = timeForCurrentFrame - frameTime;
        }
        velY += Game.gravity;
        velX += actX;
        if (actX == 0) {
            if (velX > 1 || velX < -1) {
                if (velX > 0) {
                    velX -= 1;
                } else {
                    velX += 1;
                }
            } else {
                velX = 0;
            }
        } else if (velX > maxSpeed) {
            velX = (float) maxSpeed;
        } else if (velX < -maxSpeed) {
            velX = (float) -maxSpeed;
        }

        y = y + velY;
        x = x + velX;
        stay = false;
        if (y + velY > Game.screenHeight - height) {
            y = Game.screenHeight - height;
            velY = 0;
            stay = true;
        }
    }

    public void jump() {
        if (stay) {
            velY = -15;
            stay = false;
        }
    }

    public boolean isStay() {
        return stay;
    }

    public void setStay(boolean stay) {
        this.stay = stay;
    }
}
