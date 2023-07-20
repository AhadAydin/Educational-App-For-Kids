package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DirectionsGame extends AppCompatActivity {

    ObjectSelector directionSet;
    ImageView image_view;
    String currentDir;

    ProgressBar progress_bar;
    EnergyBar energy_bar;
    int maxEnergy = 3, correctAnswer=0;
    Animation shakeAnim;
    TextView info;
    Handler buttonHandler = new Handler();
    boolean buttonsActive=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions_game);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        progress_bar =findViewById(R.id.energy_bar);
        energy_bar = new EnergyBar(progress_bar,maxEnergy);
        info=findViewById(R.id.txt_direction_info);

        directionSet=new ObjectSelector();

        // directions
        directionSet.putObj("up");
        directionSet.putObj("down");
        directionSet.putObj("right");
        directionSet.putObj("left");
        directionSet.putObj("front");
        directionSet.putObj("behind");
        image_view=findViewById(R.id.direction_img);

        currentDir=(String) directionSet.randUnselectedObj();
        Log.d("project",currentDir);
        SetImageView(currentDir);

        shakeAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.wrong_answer_anim);
    }
    void SetImageView(String dir){
        switch (dir){
            case "up":
                image_view.setImageResource(R.drawable.arrow_up);
                break;
            case "down":
                image_view.setImageResource(R.drawable.arrow_down);
                break;
            case "right":
                image_view.setImageResource(R.drawable.arrow_right);
                break;
            case "left":
                image_view.setImageResource(R.drawable.arrow_left);
                break;
            case "front":
                image_view.setImageResource(R.drawable.arrow_front);
                break;
            case "behind":
                image_view.setImageResource(R.drawable.arrow_behind);
                break;
            case "noMoreDir":break;
        }
    }

    public void OnDirButtonPressed(View view) {
        if(buttonsActive){
            boolean answer=true;
            switch (currentDir){
                case "up":
                    if(((Button)view).getId() != R.id.button_up){
                        answer=false;
                    }
                    break;
                case "down":
                    if(((Button)view).getId() != R.id.button_down){
                        answer=false;
                    }
                    break;
                case "right":
                    if(((Button)view).getId() != R.id.button_right){
                        answer=false;
                    }
                    break;
                case "left":
                    if(((Button)view).getId() != R.id.button_left){
                        answer=false;
                    }
                    break;
                case "front":
                    if(((Button)view).getId() != R.id.button_front){
                        answer=false;
                    }
                    break;
                case "behind":
                    if(((Button)view).getId() != R.id.button_behind){
                        answer=false;
                    }
                    break;
                case "noMoreDir":break;
            }
            if(!answer){
                info.setText(R.string.try_again);
                energy_bar.addEnergy(-1);
                image_view.startAnimation(shakeAnim);
            }else{
                info.setText(R.string.correct_answer);
                correctAnswer++;
            }
            buttonsActive=false;
            if(!energy_bar.isZero())
                dirButtonRunnable(answer);
            else
                outOfEnergy();
        }
    }

    private void dirButtonRunnable(boolean answer){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(answer){
                    currentDir=(String) directionSet.randUnselectedObj();
                    if(currentDir!=null){
                        SetImageView(currentDir);
                        info.setText(R.string.directions_game_message);
                        buttonsActive=true;
                    }
                    else{ // game completed
                        currentDir="noMoreDir";
                        win();
                    }
                }else{
                    info.setText(R.string.directions_game_message);
                    buttonsActive=true;
                }
            }
        };
        buttonHandler.postDelayed(runnable,500);
    }

    private void win(){

        Intent intent = new Intent(this,GameFinished.class);
        intent.putExtra("xp",Math.round((float)correctAnswer/6*100));
        startActivity(intent);
    }

    private void outOfEnergy(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                win();
            }
        };
        info.setText(getString(R.string.out_of_energy));
        buttonHandler.postDelayed(runnable,700);
    }

}