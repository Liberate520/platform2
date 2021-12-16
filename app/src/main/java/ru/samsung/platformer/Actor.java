package ru.samsung.platformer;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class Actor {
    float x, y, velX, velY;
    int width, height;
    Bitmap bitmap;
    public abstract void onDraw(Canvas canvas);
    public abstract void update(int ms);

    public Actor(float x, float y, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
    }
}
