package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DaysGame extends AppCompatActivity {

    Button dayButton0,dayButton1,dayButton2,dayButton3,dayButton4,dayButton5,dayButton6; boolean buttonsActive=true;
    String currentDay = "empty";
    ObjectSelector daySet;

    ProgressBar progress_bar;
    EnergyBar energy_bar;
    int maxEnergy = 3; int correctAnswer = 0; TextView game_info;
    Animation shakeAnim; Handler handler = new Handler();
    CardView tutorialCard; TextView tutorialMsg; int tutorialMsgCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_game);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        daySet=new ObjectSelector();
        game_info = findViewById(R.id.days_game_message);

        progress_bar=findViewById(R.id.days_energy_bar);
        energy_bar = new EnergyBar(progress_bar,maxEnergy);

        //region add days to days set
        daySet.putObj(getString(R.string.monday));
        daySet.putObj(getString(R.string.tuesday));
        daySet.putObj(getString(R.string.wednesday));
        daySet.putObj(getString(R.string.thursday));
        daySet.putObj(getString(R.string.friday));
        daySet.putObj(getString(R.string.saturday));
        daySet.putObj(getString(R.string.sunday));
        //endregion

        //region define buttons and give random days
        dayButton0 = findViewById(R.id.day_button_0);
        dayButton0.setText((String)daySet.randUnselectedObj());

        dayButton1 = findViewById(R.id.day_button_1);
        dayButton1.setText((String)daySet.randUnselectedObj());

        dayButton2 = findViewById(R.id.day_button_2);
        dayButton2.setText((String)daySet.randUnselectedObj());

        dayButton3 = findViewById(R.id.day_button_3);
        dayButton3.setText((String)daySet.randUnselectedObj());

        dayButton4 = findViewById(R.id.day_button_4);
        dayButton4.setText((String)daySet.randUnselectedObj());

        dayButton5 = findViewById(R.id.day_button_5);
        dayButton5.setText((String)daySet.randUnselectedObj());

        dayButton6 = findViewById(R.id.day_button_6);
        dayButton6.setText((String)daySet.randUnselectedObj());
        //endregion

        shakeAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.wrong_answer_anim);

        // get intent for playing tutorial or not
        tutorialCard = findViewById(R.id.daysGame_tutorialCard);
        tutorialMsg  =findViewById(R.id.daysGame_tutorialMsg);
        Intent intent = getIntent();
        if (intent.getBooleanExtra("tutorial",false)){ // adjust tutorial scene
            buttonsActive=false;
            tutorialCard.setVisibility(View.VISIBLE);
            tutorialMsgCount++;
            tutorialMsg.setText(getString(R.string.soho_dayMsg_1) + "\n" + getString(R.string.soho_msg_clickToCont));
        }
    }

    public void OnDayGameTutorialButtonClicked(View view){
        switch (tutorialMsgCount){
            case 1:
                tutorialMsg.setText(getString(R.string.soho_dayMsg_2) + "\n" + getString(R.string.soho_msg_clickToCont));
                break;
            case 2:
                tutorialMsg.setText(getString(R.string.soho_dayMsg_3) + "\n" + getString(R.string.soho_msg_clickToCont));
                break;
            case 3:
                tutorialMsg.setText(getString(R.string.soho_dayMsg_4) + "\n" + getString(R.string.soho_msg_clickToCont));
                break;
            case 4:
                tutorialMsg.setText(getString(R.string.soho_dayMsg_5) + "\n" + getString(R.string.soho_msg_clickToCont));
                break;
            case 5:
                tutorialMsg.setText(getString(R.string.soho_dayMsg_6) + "\n" + getString(R.string.soho_msg_clickToCont));
                break;
            case 6:
                tutorialCard.setVisibility(View.INVISIBLE);
                buttonsActive=true;
                break;
        }
        tutorialMsgCount++;
    }

    public void OnDayButtonClicked(View view){
        if(buttonsActive){
            boolean answer=true;
            if(currentDay == "empty"){
                if(((Button)view).getText() == getString(R.string.monday)){
                    currentDay=getString(R.string.monday);
                    Log.d("day_msg","monday");
                    findViewById(R.id.day_label_monday).setVisibility(View.VISIBLE);
                }else{
                    answer=false;
                }
            }else if(currentDay == getString(R.string.monday)){
                if(((Button)view).getText() == getString(R.string.tuesday)){
                    currentDay=getString(R.string.tuesday);
                    Log.d("day_msg","tuesday");
                    findViewById(R.id.day_label_tuesday).setVisibility(View.VISIBLE);
                }else{
                    answer=false;
                }
            }else if(currentDay == getString(R.string.tuesday)){
                if(((Button)view).getText() == getString(R.string.wednesday)){
                    currentDay=getString(R.string.wednesday);
                    Log.d("day_msg","wednesday");
                    findViewById(R.id.day_label_wednesday).setVisibility(View.VISIBLE);
                }else{
                    answer=false;
                }
            }else if(currentDay == getString(R.string.wednesday)){
                if(((Button)view).getText() == getString(R.string.thursday)){
                    currentDay=getString(R.string.thursday);
                    Log.d("day_msg","thursday");
                    findViewById(R.id.day_label_thursday).setVisibility(View.VISIBLE);
                }else{
                    answer=false;
                }
            }else if(currentDay == getString(R.string.thursday)){
                if(((Button)view).getText() == getString(R.string.friday)){
                    currentDay=getString(R.string.friday);
                    Log.d("day_msg","friday");
                    findViewById(R.id.day_label_friday).setVisibility(View.VISIBLE);
                }else{
                    answer=false;
                }
            }else if(currentDay == getString(R.string.friday)){
                if(((Button)view).getText() == getString(R.string.saturday)){
                    currentDay=getString(R.string.saturday);
                    Log.d("day_msg","saturday");
                    findViewById(R.id.day_label_saturday).setVisibility(View.VISIBLE);
                }else{
                    answer=false;
                }
            }else if(currentDay == getString(R.string.saturday)){
                if(((Button)view).getText() == getString(R.string.sunday)){
                    currentDay=getString(R.string.sunday);
                    Log.d("day_msg","sunday");
                    findViewById(R.id.day_label_sunday).setVisibility(View.VISIBLE);
                }else{
                    answer=false;
                }
            }
            if(!answer){
                game_info.setText(getString(R.string.wrong_answer));
                energy_bar.addEnergy(-1);
                view.startAnimation(shakeAnim);
            }else{
                correctAnswer++;
                game_info.setText(getString(R.string.correct_answer));
            }
            if(energy_bar.isZero()){
                game_info.setText(R.string.out_of_energy);
                outOfEnergy();
            }else{
                buttonsActive=false;
                dayButtonRunnable();
            }
        }
    }

    private void dayButtonRunnable(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if(currentDay.equals("Sunday"))
                    gameFinished();
                else
                    game_info.setText(R.string.days_game_message);
            }
        };
        Runnable buttonRunnable = new Runnable() {
            @Override
            public void run() {
                buttonsActive=true;
                handler.postDelayed(runnable,250);
            }
        };
        handler.postDelayed(buttonRunnable,400);
    }
    private void outOfEnergy(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                gameFinished();
            }
        };
        handler.postDelayed(runnable,900);
    }
    private void gameFinished(){
        Intent intent=new Intent(this,GameFinished.class);
        intent.putExtra("xp",Math.round(((float)correctAnswer/7) * 100));
        startActivity(intent);
    }
}