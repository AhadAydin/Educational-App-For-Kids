package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Struct;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.jar.Attributes;

public class spellingGame extends AppCompatActivity {

    private static class IDwithName{
        public int imgID;
        public String name;
        public IDwithName(int id,String n){
            imgID=id;name=n;
        }
    }
    ObjectSelector imgIDSet;
    ImageView image_view;
    TextView text_view,game_message;
    EditText edit_text;
    IDwithName currentImgID;
    ProgressBar progressBar;
    Timer timer; TimerTask timerTask;double time=0; boolean game_finished=false;
    int GAME_TIME=40,correctAnswers=0;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_game);
        image_view=findViewById(R.id.imgView);
        text_view=findViewById(R.id.spelling_text_view);
        game_message=findViewById(R.id.spelling_game_msg);
        edit_text=findViewById(R.id.spelling_edit_text);
        imgIDSet=new ObjectSelector();

        loadImages();

        edit_text.addTextChangedListener(textWatcher);

        progressBar=findViewById(R.id.progressBar);
        progressBar.setMax(GAME_TIME);
        progressBar.setProgress(GAME_TIME);

        currentImgID = ((IDwithName)imgIDSet.randUnselectedObj());
        image_view.setImageDrawable(getDrawable(currentImgID.imgID));
        resetTextView(currentImgID.name);
        timer= new Timer();
        StartTimer();
    }

    private void loadImages(){
        fetchImages("apple", getString(R.string.apple),3);
        fetchImages("beach", getString(R.string.beach),3);
        fetchImages("cat", getString(R.string.cat),6);
        fetchImages("chair", getString(R.string.chair),3);
        fetchImages("cloud", getString(R.string.cloud),2);
        fetchImages("cow", getString(R.string.cow),4);
        fetchImages("dog", getString(R.string.dog),6);
        fetchImages("duck", getString(R.string.duck),4);
        fetchImages("elephant", getString(R.string.elephant),2);
        fetchImages("fish", getString(R.string.fish),4);
        fetchImages("flower", getString(R.string.flower),5);
        fetchImages("forest", getString(R.string.forest),4);
        fetchImages("glass", getString(R.string.glass),1);
        fetchImages("horse", getString(R.string.horse),3);
        fetchImages("ice", getString(R.string.ice),1);
        fetchImages("mountain", getString(R.string.mountain),4);
        fetchImages("orange", getString(R.string.orange),3);
        fetchImages("potato", getString(R.string.potato),2);
        fetchImages("table", getString(R.string.table),2);
        fetchImages("tomato", getString(R.string.tomato),2);
        fetchImages("train", getString(R.string.train),4);
        fetchImages("tree",  getString(R.string.tree),3);
    }
    private void fetchImages(String fileName,String name,int count){
        for(int i =0;i<count;i++){
            String currentID = "@drawable/img_" + fileName + String.valueOf(i);
            int imgRes = getResources().getIdentifier(currentID, null, getPackageName());
            imgIDSet.putObj(new IDwithName(imgRes,name));
            Log.d("fetchImage", String.valueOf(imgRes)+","+name);
        }
    }
    private void resetTextView(String txt){
        edit_text.setText("");
        char[] charArray = txt.toCharArray();
        txt ="";
        for(int i=0;i<charArray.length;i++){
            txt +="_";
        }
        edit_text.setFilters(new InputFilter[] {new InputFilter.LengthFilter(charArray.length)});
        text_view.setText(txt);
    }
    private TextWatcher textWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!game_finished){
                char[] wordArr =currentImgID.name.toCharArray();
                String finalText="";
                for(int i = 0;i<wordArr.length;i++){
                    if(i<s.length()){
                        finalText+=s.charAt(i);
                    }else{
                        finalText+="_";
                    }
                }
                text_view.setText(finalText);
                if(s.toString().toLowerCase(Locale.ROOT).equals(currentImgID.name)){
                    correctAnswer();

                }
            }
        }
    };
    private void correctAnswer(){

        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                game_message.setText(getString(R.string.spelling_info));
                currentImgID = ((IDwithName)imgIDSet.randUnselectedObj());
                if(currentImgID==null)
                    timesUp();
                image_view.setImageDrawable(getDrawable(currentImgID.imgID));
                resetTextView(currentImgID.name);
            }
        };
        correctAnswers++;
        game_message.setText(getText(R.string.correct_answer));
        handler.postDelayed(runnable,500);
    }
    private void StartTimer(){
        timerTask=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time++;
                        progressBar.setProgress(GAME_TIME-(int)time);
                        if(time>GAME_TIME && !game_finished){
                            timesUp();
                        }
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);
    }
    private void timesUp(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadFinishScreen();
            }
        };
        game_finished=true;
        edit_text.setVisibility(View.INVISIBLE);
        text_view.setVisibility(View.INVISIBLE);
        game_message.setText(getString(R.string.times_up));
        handler.postDelayed(runnable,1000);
    }
    private void loadFinishScreen(){
        Intent intent = new Intent(this,GameFinished.class);
        intent.putExtra("xp",correctAnswers*7);
        startActivity(intent);
    }
}