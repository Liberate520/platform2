package ru.samsung.platformer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class Actor {
    protected float x, y, velX, velY;
    protected int width, height;
    protected Bitmap bitmap;
    protected Paint paint = new Paint();
    public abstract void onDraw(Canvas canvas);
    public boolean onBound(Actor actor){
        Rect myRect = new Rect((int)x, (int)y, (int)x+width, (int)y+height);
        Rect actorRect = new Rect((int)actor.x, (int)actor.y,
                (int)actor.x+actor.width, (int)actor.y+actor.height);
        return myRect.intersect(actorRect);
    }

    public Actor(float x, float y, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        width = bitmap.getWidth();
        height = bitmap.getHeight();
    }
}
