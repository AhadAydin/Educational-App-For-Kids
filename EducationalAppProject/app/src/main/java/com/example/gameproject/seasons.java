package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;
public class seasons extends AppCompatActivity implements View.OnClickListener {

    Button bwin;
    Button bsum;
    Button bfall;
    Button bsp;
    ImageView seasonv;

    String season;
    boolean clicked = false;
    ArrayList<String> name =new ArrayList<>() ;
    Random random= new Random();
    ProgressBar progressBar;
    TextView pointsTextView;
    int points=0;
    int xp=0;
    int numofclick=0;
    int maxPoints=50;


    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);
        bwin= (Button) findViewById(R.id.winterbutton);
        bfall=(Button) findViewById(R.id.fallbutton);
        bsp=(Button) findViewById(R.id.springbutton);
        bsum=(Button) findViewById(R.id.summerbutton);
        seasonv=(ImageView) findViewById(R.id.seasonimage);
        progressBar = findViewById(R.id.progressBar);
        pointsTextView= findViewById(R.id.pointsTextView);

        bwin.setOnClickListener(this);
        bfall.setOnClickListener(this);
        bsp.setOnClickListener(this);
        bsum.setOnClickListener(this);



        name.add(getString(R.string.winter));
        name.add(getString(R.string.fall));
        name.add(getString(R.string.spring));
        name.add(getString(R.string.summer));


        setRandomSeason();

        points=maxPoints;
        progressBar.setProgress(points);
        pointsTextView.setText((getString(R.string.xptext))+ xp);



    }

    private void setRandomSeason() {
        index=random.nextInt(name.size());
        season=name.get(index);
        setSeasonimg();
    }

    private void setSeasonimg(){
        int resourceId= getResources().getIdentifier(season.toLowerCase(),"drawable",getPackageName());
        Glide.with(this).load(resourceId).into(seasonv);
    }

    // checks if the answer is true
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.winterbutton) {
            checkAnswer(getString(R.string.winter) );
            numofclick++;
        } else if (v.getId() == R.id.fallbutton) {
            checkAnswer(getString(R.string.fall));
            numofclick++;

        } else if (v.getId() == R.id.springbutton) {
            checkAnswer(getString(R.string.spring));
            numofclick++;

        } else if (v.getId() == R.id.summerbutton) {
            checkAnswer(getString(R.string.summer));
            numofclick++;

        }
    }



    private void checkAnswer(String selectedAnswer) {
        if(selectedAnswer.equals(season)){
            xp+=10;
            pointsTextView.setText((getString(R.string.xptext))+xp);
        }
        else{
            points-=10;
        }
        if(points<0){
            points=0;
        }
        else if(points>maxPoints){
            points=maxPoints;
        }
        if(points==0 || (numofclick==10))
        {
            //game over intenti
            Intent gameover= new Intent(this,GameFinished.class);
            gameover.putExtra("xp",xp);
            startActivity(gameover);

        }
        setRandomSeason();
        pointsTextView.setText((getString(R.string.xptext))+ xp);
        int progress=(int)((points/(float)maxPoints)*50);
        progressBar.setProgress(points);
    }
}