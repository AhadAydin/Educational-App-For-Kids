package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class ClockGame extends AppCompatActivity {
    private Handler clckHandler = new Handler();
    ImageView clock_body,clock_akrp,clock_ylkvn,digital_clock;
    TextView digital_clock_txt,digital_clock_inner;
    Button submitButton;
    NumberPicker hourPicker,minutePicker;
    int currentHour,currentMinute,pickedHour,pickedMinute;
    int level = 0,questionCount=0;
    boolean firstTry = true;
    int ANGLE_OFFSET = 90,LEVELUP_COUNT=3,SUBMIT_BUTTON_COLOR_ID;
    String[] minuteValues;
    Animation shakeAnim;
    ProgressBar progressBar;
    EnergyBar energyBar;
    int MAX_ENERGY = 7,correctAnswers=0;
    TextView infoMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock_game);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // region INITIALIZING COMPONENTS
        infoMsg=findViewById(R.id.txt_clock_info);

        clock_body=findViewById(R.id.img_clock_body);
        clock_akrp=findViewById(R.id.img_clock_akrp);
        clock_ylkvn=findViewById(R.id.img_clock_ylkvn);

        digital_clock_txt =findViewById(R.id.text_digital_clock);
        digital_clock=findViewById(R.id.img_digital_clock);
        digital_clock_inner=findViewById(R.id.text_inner_digital_clock);

        submitButton=findViewById(R.id.clock_submit_button);
        SUBMIT_BUTTON_COLOR_ID=submitButton.getDrawingCacheBackgroundColor();
        hourPicker=findViewById(R.id.hour_picker);
        hourPicker.setMinValue(1);
        hourPicker.setMaxValue(12);
        minutePicker=findViewById(R.id.minute_picker);
        minuteValues = new String[12];
        minutePicker.setMaxValue(minuteValues.length-1);
        for (int i = 0; i < minuteValues.length; i++) {
            String number = Integer.toString(i*5);
            minuteValues[i] = number.length() < 2 ? "0" + number : number;
        }
        minutePicker.setDisplayedValues(minuteValues);

        shakeAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.wrong_answer_anim);

        progressBar=findViewById(R.id.clock_progressBar);
        energyBar=new EnergyBar(progressBar,MAX_ENERGY);
        //endregion

        hourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pickedHour = newVal;
                if(level==1){
                    char[] cArray = digital_clock_inner.getText().toString().toCharArray();
                    if(newVal<10){
                        cArray[0]='0'; cArray[1]= Character.forDigit(newVal,10);
                    }else{
                        cArray[0]=Character.forDigit(newVal/10,10); cArray[1]= Character.forDigit(newVal%10,10);
                    }
                    digital_clock_inner.setText(new String(cArray));
                }
            }
        });
        minutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pickedMinute = Integer.parseInt(minuteValues[newVal]);
                if(level==1){
                    char[] cArray = digital_clock_inner.getText().toString().toCharArray();
                    if(newVal*5<10){
                        cArray[3]='0'; cArray[4]= Character.forDigit(newVal*5,10);
                    }else{
                        cArray[3]=Character.forDigit(newVal*5/10,10); cArray[4]= Character.forDigit(newVal*5%10,10);
                    }
                    digital_clock_inner.setText(new String(cArray));
                }
            }
        });

        currentHour = randomHour(12);
        currentMinute = randomHour(12) * 5;

        rotateClockPart(clock_akrp,currentHour*30);
        rotateClockPart(clock_ylkvn,currentMinute * 6);

        SetDigitalClockText();
    }

    public void submit(View view) {

        submitButton.setVisibility(View.INVISIBLE);

        if(currentMinute==pickedMinute && currentHour==pickedHour){ //TRUE
            correctAnswers++;
            firstTry=true;
            infoMsg.setText(R.string.congratulations);
            submitRunnable(true,1250);
        }else{                                                     //ELSE
            energyBar.addEnergy(-1);
            if(energyBar.isZero()){
                outOfEnergy();
            }else{
                if(level==0){
                    clock_body.startAnimation(shakeAnim);
                    clock_akrp.startAnimation(shakeAnim);
                    clock_ylkvn.startAnimation(shakeAnim);
                }else{
                    digital_clock_txt.startAnimation(shakeAnim);
                }
                if(firstTry){
                    infoMsg.setText(R.string.try_again);
                }else{
                    infoMsg.setText(R.string.wrong_answer);
                }
                Log.d("first try", String.valueOf(firstTry));
                submitRunnable(false,1000);
            }
        }


    }
    private void submitRunnable(boolean answer,int delay){

        Runnable sbmtRunnable = new Runnable() {
            @Override
            public void run() {
                if(answer){
                    firstTry=true;
                    questionCount++;
                    if(questionCount>=LEVELUP_COUNT){
                        questionCount=0;
                        level++;
                    }
                    if(level==0){
                        infoMsg.setText(R.string.clock_game_info_0);

                        currentHour = randomHour(12);
                        currentMinute = randomHour(12) * 5;

                        rotateClockPart(clock_akrp,currentHour*30);
                        rotateClockPart(clock_ylkvn,currentMinute * 6);
                    }else if(level==1){
                        infoMsg.setText(R.string.clock_game_info_1);

                        clock_body.setVisibility(View.INVISIBLE);
                        clock_akrp.setVisibility(View.INVISIBLE);
                        clock_ylkvn.setVisibility(View.INVISIBLE);
                        digital_clock_txt.setVisibility(View.VISIBLE);
                        digital_clock.setVisibility(View.VISIBLE);
                        digital_clock_inner.setVisibility(View.VISIBLE);
                        hourPicker.setMaxValue(23);

                        currentHour = randomHour(24);
                        currentMinute = randomHour(12) * 5;

                        SetDigitalClockText();
                    }else{
                        win();
                    }
                }else{
                    if(firstTry){
                        firstTry=false;
                        if(level==0){
                            infoMsg.setText(R.string.clock_game_info_0);
                        }else{
                            infoMsg.setText(R.string.clock_game_info_1);
                            clock_body.setVisibility(View.INVISIBLE);
                            clock_akrp.setVisibility(View.INVISIBLE);
                            clock_ylkvn.setVisibility(View.INVISIBLE);
                            digital_clock_txt.setVisibility(View.VISIBLE);
                            digital_clock.setVisibility(View.VISIBLE);
                            digital_clock_inner.setVisibility(View.VISIBLE);
                        }
                    }else{
                        firstTry=true;
                        questionCount++;
                        if(questionCount>=LEVELUP_COUNT){
                            questionCount=0;
                            level++;
                        }
                        if(level==0){
                            infoMsg.setText(R.string.clock_game_info_0);

                            currentHour = randomHour(12);
                            currentMinute = randomHour(12) * 5;

                            rotateClockPart(clock_akrp,currentHour*30);
                            rotateClockPart(clock_ylkvn,currentMinute * 6);
                        }else if(level==1){
                            infoMsg.setText(R.string.clock_game_info_1);

                            clock_body.setVisibility(View.INVISIBLE);
                            clock_akrp.setVisibility(View.INVISIBLE);
                            clock_ylkvn.setVisibility(View.INVISIBLE);
                            digital_clock_txt.setVisibility(View.VISIBLE);
                            digital_clock_txt.setVisibility(View.VISIBLE);
                            digital_clock.setVisibility(View.VISIBLE);
                            digital_clock_inner.setVisibility(View.VISIBLE);
                            hourPicker.setMaxValue(23);

                            currentHour = randomHour(24);
                            currentMinute = randomHour(12) * 5;

                            SetDigitalClockText();
                        }else{
                            win();
                        }
                    }
                }
                submitButton.setVisibility(View.VISIBLE);
            }
        };

        clckHandler.postDelayed(sbmtRunnable,delay);
    }

    private void rotateClockPart(ImageView cp, float angle){
        cp.setRotation(0);
        cp.setRotation(cp.getRotation() + angle - ANGLE_OFFSET);
    }

    private int randomHour(int bound){
        return new Random().nextInt(bound);
    }

    private void SetDigitalClockText(){
        String finalText;
        if(currentMinute==0){
            finalText=getString(R.string.its_clock)+" "+numberToString(currentHour)+" "+getString(R.string.oclock);
        }else{
            finalText=getString(R.string.its_clock)+" "+numberToString(currentHour)+","+numberToString(currentMinute);
        }
        digital_clock_txt.setText(finalText);
    }
    private String numberToString(int num)  {
        switch (num){
            case 0:return getString(R.string.zero);
            case 1:return getString(R.string.one);
            case 2:return getString(R.string.two);
            case 3:return getString(R.string.three);
            case 4:return getString(R.string.four);
            case 5:return getString(R.string.five);
            case 6:return getString(R.string.six);
            case 7:return getString(R.string.seven);
            case 8:return getString(R.string.eight);
            case 9:return getString(R.string.nine);
            case 10:return getString(R.string.ten);
            case 11:return getString(R.string.eleven);
            case 12:return getString(R.string.twelve);
            case 13:return getString(R.string.thirteen);
            case 14:return getString(R.string.fourteen);
            case 15:return getString(R.string.fifteen);
            case 16:return getString(R.string.sixteen);
            case 17:return getString(R.string.seventeen);
            case 18:return getString(R.string.eighteen);
            case 19:return getString(R.string.nineteen);
            case 20:return getString(R.string.twenty);
            case 21:return getString(R.string.twenty) + " " + getString(R.string.one);
            case 22:return getString(R.string.twenty) + " " + getString(R.string.two);
            case 23:return getString(R.string.twenty) + " " + getString(R.string.three);
            case 25:return getString(R.string.twenty) + " " + getString(R.string.five);
            case 30:return getString(R.string.thirty);
            case 35:return getString(R.string.thirty) + " " + getString(R.string.five);
            case 40:return getString(R.string.fourty);
            case 45:return getString(R.string.fourty) + " "+ getString(R.string.five);
            case 50:return getString(R.string.fifty);
            case 55:return getString(R.string.fifty) + " " + getString(R.string.five);
            default:return"number doesnt exist("+String.valueOf(num)+")";
        }
    }
    private void win(){
        submitButton.setVisibility(View.INVISIBLE);
        Intent intent = new Intent(this,GameFinished.class);
        intent.putExtra("xp",Math.round((float) correctAnswers/6*100));
        startActivity(intent);
    };
    private void outOfEnergy(){
        Runnable runnable= new Runnable() {
            @Override
            public void run() {
                win();
            }
        };

        submitButton.setVisibility(View.INVISIBLE);
        infoMsg.setTextColor(getColor(R.color.live_red));
        infoMsg.setText(R.string.out_of_energy);
        clckHandler.postDelayed(runnable,1000);
    }
}