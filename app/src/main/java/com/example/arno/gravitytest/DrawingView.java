package com.example.arno.gravitytest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static com.example.arno.gravitytest.MainActivity.drawingView;

public class DrawingView extends View implements Runnable {
    float density;
    float screen_height;
    float screen_width;
    int FPS = 90;
    public Bitmap bitmap = null;
    int press = 0;
    PointF position = new PointF();
    PointF velocity = new PointF(5f, -35f);
    float Gravity = 0.25f;
    float theta = 5;


    public DrawingView(Context context) {
        super(context);
        init(context);
    }

    void init(Context context) {

        screen_height = getResources().getDisplayMetrics().heightPixels;
        Log.e("height", String.valueOf(screen_height));
        screen_width = getResources().getDisplayMetrics().widthPixels;
        Log.e("width", String.valueOf(screen_width));
        density = getResources().getDisplayMetrics().density;
        Log.e("density", String.valueOf(density));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball, options);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (100f * density), (int) (100f * density), false);
        position.x = (screen_width / 2);
        position.y = (screen_height) - ((250f * density) / 2);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int id = event.getAction();
        float x = event.getX();
        float y = event.getY();
        float xS = (float) Math.pow((x - position.x) , 2);
        float yS = (float) Math.pow((y - position.y) , 2);
        float root = (float) Math.sqrt(xS + yS);
        switch (id) {
            case MotionEvent.ACTION_DOWN:
                if (press != 2) {

                    press = 1;
                } else {
                    if(root < (50f*density) ) {
                        velocity = new PointF(velocity.x, -35f);
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:

                press = 2;
                return true;


        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        if (position.y < (50f*density)) {
            velocity.y = 25f;
        }

        if(position.x > (screen_width - (50f*density))){
            velocity.x = -5f;
        }
        if(position.x < (50f*density)){
            velocity.x = 5f;
        }


        if (press == 2) {
            Log.d("event occurred", String.valueOf(press));
            position.offset(velocity.x, velocity.y);
            velocity.offset(0, Gravity);
            theta += 5;


        }


        canvas.drawColor(Color.CYAN);
        canvas.save();
        canvas.translate((int) (position.x), (int) (position.y));
        canvas.rotate(theta);
        canvas.drawBitmap(bitmap, (-1) * (50f * density), (-1) * (50f * density), null);
        canvas.restore();


    }


    @Override
    public void run() {
        while (!Thread.interrupted()) {

            try {
                Thread.sleep(1000 / FPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            postInvalidate();
        }

    }
}
