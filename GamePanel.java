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
    //Drawable swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);


    private int swingSpeed = 10;                //CHANGE SWING TIME HERE


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
        smashTimer = randomNumber.nextInt((200 - 100) + 1) + 100;
        swingTimer = swingSpeed;
        text.setColor(Color.BLACK);
        text.setTextSize(100);
        text.setStyle(Paint.Style.FILL);
        resetText.setColor(Color.BLACK);
        resetText.setTextSize(50);
        buttonLook.setColor(Color.GRAY);





        drawable.setBounds((width/2 - 100), (int)(height * 0.7 - 100), (width/2 + 100), (int)(height * 0.7 + 100));




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
            case MotionEvent.ACTION_MOVE:
                if((int) event.getX() >= (width/2 - 100) &&                     //button location
                        (int) event.getX() <= (width/2 + 100) &&                //press button
                        (int) event.getY() >= (height * 0.7 - 100) &&           //touch = true, used for cases in update()
                        (int) event.getY() <= (height * 0.7 + 100) && !tooSoon)
                {
                    touch = true;
                }
                else {
                    touch = false;
                }
                if((int) event.getX() >= (width * 0.83 - 100) &&                  //reset button location
                        (int) event.getX() <= (width * 0.83 + 100) &&              //resets the game
                        (int) event.getY() >= (height * 0.95 - 50) &&               //calls resetGame()
                        (int) event.getY() <= (height * 0.95 + 50)){
                    resetGame();
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
          /*  switch(swingTimer) {
                case 10:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 9:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 8:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 7:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 6:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 5:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 4:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 3:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 2:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 1:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
                case 0:
                    swingAnimation = swingAnimation = getResources().getDrawable(R.drawable.swingAnimation_01);
                    break;
            }*/
            swingTimer--;                           //animate here
            if(swingTimer == 0){
                smash = true;
                swing = false;
            }
        }
        if(touch && smash){                         //get hit
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
        canvas.drawText("Score: " + pointCount/10,((width/2) - 185),(int)(height * 0.25), text);
        canvas.drawText("RESET",(int)(width * 0.83),(int)(height * 0.95), resetText);
        canvas.drawText("smash: " + smashTimer,(width/2),(height/2)+ 100, text);                    //temp
        canvas.drawText("swing: " + swingTimer,(width/2),(height/2)+ 300, text);                    //temp
        if(win){
            canvas.drawText("win",(width/2) - 80,(int)(height * 0.2), text);
        }
        if(lose){
            canvas.drawText("lose",(width/2) - 80,(int)(height * 0.2), text);
        }
        if(tooSoon){
            canvas.drawText("too soon",(width/2) - 80,(int)(height * 0.2), text);
        }
        drawable.draw(canvas);
        //swingAnimation.draw(canvas);
    }


}
