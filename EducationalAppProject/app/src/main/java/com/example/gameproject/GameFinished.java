package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import pl.droidsonroids.gif.GifDrawable;

public class GameFinished extends AppCompatActivity {

    Button screenButton; TextView xp_text;
    Handler handler = new Handler();
    int gainedXp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_finished);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        Intent intent = getIntent();
        gainedXp = intent.getIntExtra("xp",0);
        xp_text = findViewById(R.id.gameFin_xp);
        xp_text.setText("+" + String.valueOf(gainedXp) + " xp");

        screenButton = findViewById(R.id.gameFin_tapScreenButton);
        handler.postDelayed(runnable,500);
    }



    @Override
    public void onBackPressed() {

    }

    public void gameFinTapScreen(View view) {
        Intent intent = new Intent(this, MainMenu.class);
        intent.putExtra("xp",gainedXp);
        startActivity(intent);
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            screenButton.setVisibility(View.VISIBLE);
        }
    };
}