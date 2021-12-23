package ru.samsung.platformer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    SurfaceHolder surfaceHolder;
    Timer timer;
    Bitmap bitmapPlayer, bitmapGround5;
    Player player;
    static List<Ground> grounds = new ArrayList<>();
    static final int gravity = 1;
    final int timeInterval = 20;
    static int screenWidth, screenHeight;
    Joystick joystick;

    public Game(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        bitmapPlayer = BitmapFactory.decodeResource(getResources(), R.drawable.player_run);
        bitmapGround5 = BitmapFactory.decodeResource(getResources(), R.drawable.surface5);
        bitmapGround5 = Bitmap.createScaledBitmap(bitmapGround5, 100, 100, true);
        player = new Player(0,0, bitmapPlayer, 6);
        grounds.add(new Ground(0, 200, bitmapGround5));

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;

        joystick = new Joystick(120, screenHeight - 120,
                80, 40);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        timer.cancel();
    }

    public void update(){
        joystick.update();
        player.update(timeInterval, joystick.getActuatorX(), joystick.getActuatorY());
        for (Ground ground: grounds) {
            if (player.onBound(ground)){
                player.y = ground.y - player.height;
                player.velY = 0;
                player.setStay(true);
            }
        }
    }

    public void draw(){
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLUE);
        for (Ground ground: grounds) {
            ground.onDraw(canvas);
        }
        player.onDraw(canvas);
        joystick.draw(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (joystick.isPressed(event.getX(), event.getY())){
                    joystick.setIsPressed(true);
                } else {
                    player.jump();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (!joystick.getIsPressed() && joystick.isPressed(event.getX(1), event.getY(1))){
                    joystick.setIsPressed(true);
                } else {
                    player.jump();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (joystick.getIsPressed()){
                    joystick.setActuator(event.getX(), event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                if (joystick.getIsPressed()){
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                break;
        }
        return true;
    }

    class Timer extends CountDownTimer{

        public Timer() {
            super(Integer.MAX_VALUE, timeInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            update();
            draw();
        }

        @Override
        public void onFinish() {

        }
    }
}
