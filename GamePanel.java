package com.example.jason.fingersmash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;

    int width = getResources().getDisplayMetrics().widthPixels;
    int height = getResources().getDisplayMetrics().heightPixels;
    private Rect rectangle;
    int livesLeft = 5;
    int originX = width
    int originY = 0;
    private int color;


    public GamePanel(Context context){

        super(context);
        rectangle.set(int)(width - width * 0.5, height + height * 0.5, width + width * 0.5, height - height * 0.5);

        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }
    public void update(Point point)
    {
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);
    }
    public boolean isinRectangle(int x, int y)
    {

    }
    @Override
    public void draw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN
                boolean holdButton = false;
                if (holdButton == true) {
                    if ((int) event.getX() > width - width * 0.5 && (int) event.getX() >= width + width * 0.5 && (int) event.getY() >= height - height * 0.5 &&
                            (int) event.getY() >= height + height * 0.5) {


                    }
                }
            case MotionEvent.ACTION_UP

        return true;
    }

    public void update(){

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);

    }

}
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
    }

}
