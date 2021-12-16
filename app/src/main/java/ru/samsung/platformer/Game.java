package ru.samsung.platformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    boolean work = true;
    GameThread gameThread;
    Bitmap bitmapPlayer;
    Player player;

    public Game(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        bitmapPlayer = BitmapFactory.decodeResource(getResources(), R.drawable.player_run);
        player = new Player(0,0, bitmapPlayer);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        gameThread = new GameThread();
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        work = false;
        boolean retry = true;
        while (retry){
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class GameThread extends Thread{
        @Override
        public void run() {

            while (work){
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawColor(Color.BLUE);
                player.update(5);
                player.onDraw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
