package com.example.arno.gravitytest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    public static DrawingView drawingView;
    Thread game_thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        drawingView = new DrawingView(this);
        game_thread = new Thread(drawingView);
        game_thread.start();
        super.onCreate(savedInstanceState);
        setContentView(drawingView);

    }


}
