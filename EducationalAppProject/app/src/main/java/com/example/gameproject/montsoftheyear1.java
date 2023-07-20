package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class montsoftheyear1 extends AppCompatActivity {

    Button bjanuary,bfebruary,bmarch,bapril,bmay,bjune,bjuly,baugust,bseptember,boctober,bnovember,bdecember;
    TextView txtjanuary,txtfebruary,txtmarch,txtapril,txtmay,txtjune,txtjuly,txtaugust,txtseptember,txtoctober,txtnovember,txtdecember;
    ImageView arr1,arr2,arr3,arr4,arr5,arr6,arr8,arr7,arr9,arr10,arr11;
    ProgressBar progressBar;
    TextView pointText;
    String currentMonth=null;
    ArrayList<String> months= new ArrayList<>();

    int points=0;
    int xp=0;
    int max_points=50;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_montsoftheyear1);
        pointText = findViewById(R.id.puan);
        progressBar = findViewById(R.id.progressBar2);

        bjanuary = findViewById(R.id.button_january);
        bfebruary = findViewById(R.id.button_february);
        bmarch = findViewById(R.id.button_march);
        bapril = findViewById(R.id.button_april);
        bmay = findViewById(R.id.button_may);
        bjune = findViewById(R.id.button_june);
        bjuly = findViewById(R.id.button_july);
        baugust = findViewById(R.id.button_august);
        bseptember = findViewById(R.id.button_september);
        boctober = findViewById(R.id.button_october);
        bnovember = findViewById(R.id.button_november);
        bdecember = findViewById(R.id.button_december);

        txtjanuary = findViewById(R.id.textJanuary);
        txtfebruary = findViewById(R.id.textFebruary);
        txtmarch = findViewById(R.id.textMarch);
        txtapril = findViewById(R.id.textApril);
        txtmay = findViewById(R.id.textMay);
        txtjune = findViewById(R.id.textJune);
        txtjuly = findViewById(R.id.textJuly);
        txtaugust = findViewById(R.id.textAugust);
        txtseptember = findViewById(R.id.textSeptember);
        txtoctober = findViewById(R.id.textOctober);
        txtnovember = findViewById(R.id.textNovember);
        txtdecember = findViewById(R.id.textDecember);

        arr1 = findViewById(R.id.arrow1);
        arr2 = findViewById(R.id.arrow2);
        arr3 = findViewById(R.id.arrow3);
        arr4 = findViewById(R.id.arrow4);
        arr5 = findViewById(R.id.arrow5);
        arr6 = findViewById(R.id.arrow6);
        arr7 = findViewById(R.id.arrow7);
        arr8 = findViewById(R.id.arrow8);
        arr9 = findViewById(R.id.arrow9);
        arr10 = findViewById(R.id.arrow10);
        arr11= findViewById(R.id.arrow11);

        points=max_points;
        pointText.setText(getString(R.string.xptext)+xp);

    }

    public void onMonthButtonClick(View v) {

        if (currentMonth == null) {
            if (v.getId() == bjanuary.getId()) {
                txtjanuary.setVisibility(View.VISIBLE);
                arr1.setVisibility(View.VISIBLE);
                xp += 10;
                pointText.setText(getString(R.string.xptext)+xp);
                currentMonth = getString(R.string.textJanuary);
            } else {
                points -= 10;

            }
        } else if (currentMonth.equals(getString(R.string.textJanuary)) && (v.getId() == bfebruary.getId())) {
            txtfebruary.setVisibility(View.VISIBLE);
            arr2.setVisibility(View.VISIBLE);
            xp += 10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textfebruary);
        } else if (currentMonth.equals(getString(R.string.textfebruary)) && (v.getId() == bmarch.getId())) {
            txtmarch.setVisibility(View.VISIBLE);
            arr3.setVisibility(View.VISIBLE);
            xp += 10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textmarch);
        } else if (currentMonth.equals(getString(R.string.textmarch)) && (v.getId() == bapril.getId())) {
            txtapril.setVisibility(View.VISIBLE);
            arr4.setVisibility(View.VISIBLE);
            xp += 10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textapril);
        } else if (currentMonth.equals(getString(R.string.textapril)) && (v.getId() == bmay.getId())) {
            txtmay.setVisibility(View.VISIBLE);
            arr5.setVisibility(View.VISIBLE);
            xp += 10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textmay);
        } else if (currentMonth.equals(getString(R.string.textmay)) && (v.getId() == bjune.getId())) {
            txtjune.setVisibility(View.VISIBLE);
            arr6.setVisibility(View.VISIBLE);
            xp += 10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textjune);
        } else if (currentMonth.equals(getString(R.string.textjune)) && (v.getId() == bjuly.getId())) {
            txtjuly.setVisibility(View.VISIBLE);
            arr7.setVisibility(View.VISIBLE);
            xp += 10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textjuly);
        } else if (currentMonth.equals(getString(R.string.textjuly)) && (v.getId() == baugust.getId())) {
            txtaugust.setVisibility(View.VISIBLE);
            arr8.setVisibility(View.VISIBLE);
            xp+=10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textaugust);
        } else if (currentMonth.equals(getString(R.string.textaugust)) && (v.getId() == bseptember.getId())) {
            txtseptember.setVisibility(View.VISIBLE);
            arr9.setVisibility(View.VISIBLE);
            xp += 10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textseptember);
        } else if (currentMonth.equals(getString(R.string.textseptember)) && (v.getId() == boctober.getId())) {
            txtoctober.setVisibility(View.VISIBLE);
            arr10.setVisibility(View.VISIBLE);
            xp += 10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textoctober);
        } else if (currentMonth.equals(getString(R.string.textoctober)) && (v.getId() == bnovember.getId())) {
            txtnovember.setVisibility(View.VISIBLE);
            arr11.setVisibility(View.VISIBLE);
            xp+=10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textnovember);
        } else if (currentMonth.equals(getString(R.string.textnovember)) && (v.getId() == bdecember.getId())) {
            txtdecember.setVisibility(View.VISIBLE);
            xp += 10;
            pointText.setText(getString(R.string.xptext)+xp);
            currentMonth = getString(R.string.textdecember);
            //game over boku
            Intent gameover = new Intent(this, GameFinished.class);
            gameover.putExtra("xp",xp);
            startActivity(gameover);
        }
        else  {
            points -= 10;

        }



        if (points < 0) {
            points = 0;
        } else if (points > max_points) {
            points = max_points;
        }
        if (points == 0) {
            //game over boku
            Intent gameover = new Intent(this, GameFinished.class);
            gameover.putExtra("xp",xp);
            startActivity(gameover);
        }

        pointText.setText(getString(R.string.xptext)+xp);
        int progress = (int) ((points / (float) max_points) * 50);
        progressBar.setProgress(points);
    }

}