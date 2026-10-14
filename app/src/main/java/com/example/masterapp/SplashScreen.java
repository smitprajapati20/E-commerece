package com.example.masterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {

    ImageView imageView;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.imageview);
        sp = getSharedPreferences(ConstantSP.DataB, MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getInt(ConstantSP.userid, 0) == 0){
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                    finish();
                }
                else {
                    startActivity(new Intent(SplashScreen.this, DashboardActivity.class));
                    finish();
                }
            }
        }, 2000);

        Glide.with(this)
                .load("https://imgs.search.brave.com/q1aNULmIj5U62nAAvCHHSqBnErnEML8xg4ZuwxsDnk0/rs:fit:860:0:0:0/g:ce/aHR0cHM6Ly9tZWRp/YTIuZ2lwaHkuY29t/L21lZGlhL3YxLlky/bGtQVGM1TUdJM05q/RXhkSGx0YUdoNWVq/Y3phekl3Y21wMWNu/WTROekZpZVdZMWJX/UjFaMlJyWW5KaVpu/RTRhVGg0WmlabGNE/MTJNVjluYVdaelgz/TmxZWEpqYUNaamRE/MW4veFRpVG5ldDd4/UnYxR1BUU2hpL2dp/cGh5LmdpZg.gif")
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}