package com.example.jason.fingersmash;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;

    int width = getResources().getDisplayMetrics().widthPixels;
    int height = getResources().getDisplayMetrics().heightPixels;
    int pointCount;
    Paint text = new Paint();
    Paint resetText = new Paint();
    Paint buttonLook = new Paint();
    private boolean clicked;
    private boolean win;
    private boolean lose;
    private boolean tooSoon;
    private boolean touch;
    private boolean swing;
    private boolean smash;
    private Random randomNumber = new Random();
    private int smashTimer;
    private int swingTimer;

    Drawable drawable = getResources().getDrawable( R.drawable.buttonx, null );
    Drawable swingAnimation;

    Drawable hammerAnimation_0 = getResources().getDrawable( R.drawable.hammeranimation0, null );
    Drawable hammerAnimation_1 = getResources().getDrawable( R.drawable.hammeranimation1, null );
    Drawable hammerAnimation_2 = getResources().getDrawable( R.drawable.hammeranimation2, null );
    Drawable hammerAnimation_3 = getResources().getDrawable( R.drawable.hammeranimation3, null );
    Drawable hammerAnimation_4 = getResources().getDrawable( R.drawable.hammeranimation4, null );
    Drawable hammerAnimation_5 = getResources().getDrawable( R.drawable.hammeranimation5, null );
    Drawable hammerAnimation_6 = getResources().getDrawable( R.drawable.hammeranimation6, null );
    Drawable hammerAnimation_7 = getResources().getDrawable( R.drawable.hammeranimation7, null );





    private int swingSpeed = 7;                //CHANGE SWING TIME HERE
                                                //ALSO NEEDS TO CHANGE IN SWITCH/CASE in draw

    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);

        clicked = false;
        win = false;
        lose = false;
        tooSoon = false;
        touch = false;
        swing = false;
        smash = false;

        pointCount = 0;
        smashTimer = randomNumber.nextInt((200 - 100) + 1) + 100;       //  ((max - min) + 1) + min
        swingTimer = swingSpeed;
        text.setColor(Color.BLACK);
        text.setTextSize(100);
        text.setStyle(Paint.Style.FILL);
        resetText.setColor(Color.BLACK);
        resetText.setTextSize(50);
        buttonLook.setColor(Color.GRAY);

        drawable.setBounds((width/2 - 100), (int)(height * 0.7 - 100), (width/2 + 100), (int)(height * 0.7 + 100));


        hammerAnimation_0.setBounds((int)(width * 0.4), (int)(height * 0.2), (int)(width * 1.3), (int)(height * 0.7));
        hammerAnimation_1.setBounds((int)(width * 0.4), (int)(height * 0.2), (int)(width * 1.3), (int)(height * 0.7));
        hammerAnimation_2.setBounds((int)(width * 0.4), (int)(height * 0.2), (int)(width * 1.3), (int)(height * 0.7));
        hammerAnimation_3.setBounds((int)(width * 0.4), (int)(height * 0.2), (int)(width * 1.3), (int)(height * 0.7));
        hammerAnimation_4.setBounds((int)(width * 0.4), (int)(height * 0.2), (int)(width * 1.3), (int)(height * 0.7));
        hammerAnimation_5.setBounds((int)(width * 0.4), (int)(height * 0.2), (int)(width * 1.3), (int)(height * 0.7));
        hammerAnimation_6.setBounds((int)(width * 0.4), (int)(height * 0.2), (int)(width * 1.3), (int)(height * 0.7));
        hammerAnimation_7.setBounds((int)(width * 0.4), (int)(height * 0.2), (int)(width * 1.3), (int)(height * 0.7));




        setFocusable(true);
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
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if((int) event.getX() >= (width * 0.83 - 100) &&                  //reset button location
                        (int) event.getX() <= (width * 0.83 + 100) &&              //resets the game
                        (int) event.getY() >= (height * 0.95 - 50) &&               //calls resetGame()
                        (int) event.getY() <= (height * 0.95 + 50)){
                    resetGame();
                }
            case MotionEvent.ACTION_MOVE:
                if((int) event.getX() >= (width/2 - 100) &&                     //button location
                        (int) event.getX() <= (width/2 + 100) &&                //press button
                        (int) event.getY() >= (height * 0.7 - 100) &&           //touch = true, used for cases in update()
                        (int) event.getY() <= (height * 0.7 + 100) && !tooSoon)
                {
                    touch = true;
                }
                else {
                    if(touch == true){
                        clicked = true;
                        touch = false;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if(touch == true){
                    clicked = true;
                }
                touch = false;
        }
        return true;
    }

    public void update(){
        if(touch && !smash){                        //holding down button
            pointCount++;
            smashTimer--;
            if(smashTimer == 0){
                swing = true;//swing
            }
        }
        if(swing){
            swingTimer--;                           //animate here
            if(swingTimer == 0){
                smash = true;
                swing = false;
            }
        }
        if(touch && smash && !win){                         //get hit
            lose = true;
            pointCount = 0;
        }
        if(!touch && swing && !smash ) {            //dodge
            win = true;
        }
        if(!touch && !swing && !smash && clicked){   //too soon
            tooSoon = true;
        }
    }

    public void resetGame(){
        win = false;
        lose = false;
        tooSoon = false;
        touch = false;
        swing = false;
        smash = false;
        clicked = false;
        pointCount = 0;
        smashTimer = randomNumber.nextInt((200 - 100) + 1) + 100;
        swingTimer = swingSpeed;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        drawable.draw(canvas);
        switch(swingTimer){
            case 7:
                hammerAnimation_0.draw(canvas);
                break;
            case 6:
                hammerAnimation_1.draw(canvas);
                break;
            case 5:
                hammerAnimation_2.draw(canvas);
                break;
            case 4:
                hammerAnimation_3.draw(canvas);
                break;
            case 3:
                hammerAnimation_4.draw(canvas);
                break;
            case 2:
                hammerAnimation_5.draw(canvas);
                break;
            case 1:
                hammerAnimation_6.draw(canvas);
                break;
            case 0:
                hammerAnimation_7.draw(canvas);
                break;
        }
        canvas.drawText("Score: " + pointCount/10,((width/2) - 185),(int)(height * 0.25), text);
        canvas.drawText("RESET",(int)(width * 0.83),(int)(height * 0.95), resetText);
        //canvas.drawText("smash: " + smashTimer,(int)(width*0.15),(height/2)+ 100, text);                    //temp
        //canvas.drawText("swing: " + swingTimer,(int)(width*0.15),(height/2)+ 300, text);                    //temp
        if(win){
            canvas.drawText("win",(width/2) - 80,(int)(height * 0.2), text);
        }
        if(lose){
            canvas.drawText("lose",(width/2) - 80,(int)(height * 0.2), text);
        }
        if(tooSoon){
            canvas.drawText("too soon",(width/2) - 80,(int)(height * 0.2), text);
        }
    }


}
