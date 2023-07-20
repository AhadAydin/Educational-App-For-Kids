package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Random;

public class similarPicGame extends AppCompatActivity {


    private static class IDwithName{
        public int imgID;
        public String imgName;
        public IDwithName(int id,String n){
            imgID=id;
            imgName =n;
        }
    }

    ObjBool[] imgButtons = new ObjBool[6];
    IDwithName currentImgID;
    IDwithName currentImageID; int currentSimilarButtonsCount=0;
    ObjBool[] imgIDs;int totalImgIDCount = 0;
    ImageView mainImage;
    Animation shakeAnim;
    ProgressBar progressBar; TextView gameInfo;
    EnergyBar energyBar; int MAX_ENERGY=6, MAX_QUESTION = 10, questionCount = 0;
    Handler handler = new Handler(); boolean buttonsActive=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_pic_game);

        mainImage=findViewById(R.id.similar_main_image);
        imgButtons[0]= new ObjBool(findViewById(R.id.similar_img_button0));
        imgButtons[1]= new ObjBool(findViewById(R.id.similar_img_button1));
        imgButtons[2]= new ObjBool(findViewById(R.id.similar_img_button2));
        imgButtons[3]= new ObjBool(findViewById(R.id.similar_img_button3));
        imgButtons[4]= new ObjBool(findViewById(R.id.similar_img_button4));
        imgButtons[5]= new ObjBool(findViewById(R.id.similar_img_button5));

        gameInfo=findViewById(R.id.similar_image_game_info);
        shakeAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.wrong_answer_anim);
        progressBar=findViewById(R.id.similar_pic_energy_bar);
        energyBar = new EnergyBar(progressBar,6);

        imgIDs = new ObjBool[70];
        loadImages();
        adjustImages();
    }

    public void onSimilarImageButtonClick(View view) {
        if(buttonsActive){
            if(((Button)view).getText().equals(((IDwithName)currentImageID).imgName)) {
                currentSimilarButtonsCount--;
                view.setVisibility(View.INVISIBLE);
                CorrectAnswer(600);
            }else{
                view.startAnimation(shakeAnim);
                FalseAnswer(600);
            }
        }
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
        fetchImages("horse", getString(R.string.horse),3);
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
            imgIDs[totalImgIDCount] = new ObjBool(new IDwithName(imgRes,name));
            totalImgIDCount++;
        }
    }
    private void adjustImages(){

        currentImageID = randomIdWithName();
        mainImage.setImageDrawable(getDrawable(currentImageID.imgID));
        currentSimilarButtonsCount=0;

        for(int i = 0;i<6;i++){
            imgButtons[i].selected=false;
            ((View)imgButtons[i].obj).setVisibility(View.VISIBLE);
        }

        int maxSimilarImgCount = 3,imgCount=0;
        boolean foundSimilar=false;

        for(int i =0;i<totalImgIDCount;i++){
            if(((IDwithName)imgIDs[i].obj).imgName.equals(currentImageID.imgName)) {
                if(!imgIDs[i].selected){
                    imgCount++;
                    foundSimilar=true;
                    imgIDs[i].selected=true;
                    currentSimilarButtonsCount++;
                    Button currentButton = randomButton();
                    currentButton.setBackground(getDrawable(((IDwithName)imgIDs[i].obj).imgID));
                    currentButton.setText(((IDwithName)imgIDs[i].obj).imgName);
                }
            }else if(foundSimilar){
                break;
            }
            if(imgCount==maxSimilarImgCount){
                break;
            }
        }
        for (int i = 0;i<6-imgCount;i++){
            IDwithName tempImageId = randomIdWithName();
            Log.d("imageArray",tempImageId.imgName);
            Button tempButton = randomButton();
            tempButton.setBackground(getDrawable(tempImageId.imgID));
            tempButton.setText(tempImageId.imgName);
            if(tempButton.getText().equals(((IDwithName)currentImageID).imgName)){
                currentSimilarButtonsCount++;
            }
        }

        resetImageArray();
    }
    Runnable buttonDelay=new Runnable() {
        @Override
        public void run() {
            buttonsActive=true;
        }
    };
    private void CorrectAnswer(int delay){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {

                gameInfo.setText(R.string.similarPic_info);
                if (currentSimilarButtonsCount <= 0) {
                    questionCount++;
                    if(questionCount >= MAX_QUESTION){
                        win();
                    }else{
                        adjustImages();
                    }
                }
            }
        };

        buttonsActive=false;
        gameInfo.setText(R.string.correct_answer);
        handler.postDelayed(buttonDelay,200);
        handler.postDelayed(runnable,delay-200);
    }
    private void FalseAnswer(int delay){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                gameInfo.setText(R.string.similarPic_info);
                if(energyBar.isZero()){
                    outOfEnergy();
                }
            }
        };
        gameInfo.setText(R.string.wrong_answer);
        energyBar.addEnergy(-1);
        handler.postDelayed(buttonDelay,200);
        handler.postDelayed(runnable,delay-200);
    }
    private Button randomButton(){

        while (true){
            int index = new Random().nextInt(6);
            if(!imgButtons[index].selected){
                imgButtons[index].selected=true;
                return (Button)imgButtons[index].obj;
            }
        }
    }
    private IDwithName randomIdWithName(){
        while (true){
            int index = new Random().nextInt(totalImgIDCount-1);
            if(!imgIDs[index].selected){
                imgIDs[index].selected=true;
                return (IDwithName) imgIDs[index].obj;
            }
        }
    }
    private void resetImageArray(){
        for(int i = 0;i<totalImgIDCount;i++){
            imgIDs[i].selected=false;
        }
    }

    private void win(){
        buttonsActive=false;
        Intent intent = new Intent(this,GameFinished.class);
        intent.putExtra("xp",Math.round((float)questionCount/MAX_QUESTION*100));
        startActivity(intent);
    }
    private void outOfEnergy(){
        Intent intent = new Intent(this,GameFinished.class);
        intent.putExtra("xp",Math.round((float)questionCount/MAX_QUESTION*100));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        buttonsActive=false;
        gameInfo.setTextColor(getColor(R.color.live_red));
        gameInfo.setText(R.string.out_of_energy);
        handler.postDelayed(runnable,700);
    }
}