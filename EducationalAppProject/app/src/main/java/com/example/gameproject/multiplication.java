package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class multiplication extends AppCompatActivity implements View.OnClickListener {
    Button b1,b2,b3,b4,b5,b6;
    ImageView num1,num2,mult;
    TextView pointxt;
    EditText answer_text;
    int point=0;
    int max_points=50;
    ProgressBar progressBar;
    int level=0;
    int numofClick=0;
    Random random =new Random();
    int index;
    int temp1,temp2;
    String sa,sb,sc,sd,se,sf,sonuc;
    int xp=0;
    ArrayList<Integer> numbers= new ArrayList<>();
    ArrayList<Integer> buttontext= new ArrayList<>();
    TextView choose;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplication);
        progressBar = findViewById(R.id.progressBar4);
        pointxt = findViewById(R.id.textpuan);
        answer_text = findViewById(R.id.lvl1ans);
        num1 = findViewById(R.id.number1);
        num2 = findViewById(R.id.number2);
        mult = findViewById(R.id.imageView3);
        choose = findViewById(R.id.textView2);

        b1 = findViewById(R.id.answer1);
        b2 = findViewById(R.id.answer2);
        b3 = findViewById(R.id.answer3);
        b4 = findViewById(R.id.answer4);
        b5 = findViewById(R.id.answer5);
        b6 = findViewById(R.id.answer6);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        answer_text.setOnClickListener(this);


        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(4);
        numbers.add(5);
        numbers.add(6);
        numbers.add(7);
        numbers.add(8);
        numbers.add(9);
        numbers.add(0);

        setRandomDigit();
        setButtons();
        point=max_points;
        progressBar.setProgress(point);
        pointxt.setText((getString(R.string.xptext))+xp);


    }

    private void setRandomDigit() {
        temp2=random.nextInt(numbers.size());
        temp1=random.nextInt(numbers.size());
        setImage();
    }
    private void setImage() {
        String imageName1 = "digit_" +temp1;
        int resourceId=getResources().getIdentifier(imageName1,"drawable",getPackageName());
        num1.setImageResource(resourceId);

        String imageName2 = "digit_" +temp2;
        int resourceId2=getResources().getIdentifier(imageName2,"drawable",getPackageName());
        num2.setImageResource(resourceId2);
        setButtons();

    }

    private void setButtons() {
        int a=(temp1*temp2);
        int b=(random.nextInt(10)*random.nextInt(10));
        int c=(random.nextInt(10)*random.nextInt(10));
        int d=(random.nextInt(10)*random.nextInt(10));
        int e=(random.nextInt(10)*random.nextInt(10));
        int f=(random.nextInt(10)*random.nextInt(10));

        buttontext.clear();
        buttontext.add(a);
        buttontext.add(b);
        buttontext.add(c);
        buttontext.add(d);
        buttontext.add(e);
        buttontext.add(f);

        Collections.shuffle(buttontext);
        b1.setText(String.valueOf((buttontext.get(1))));
        b2.setText(String.valueOf((buttontext.get(2))));
        b3.setText(String.valueOf((buttontext.get(3))));
        b4.setText(String.valueOf((buttontext.get(4))));
        b5.setText(String.valueOf((buttontext.get(5))));
        b6.setText(String.valueOf((buttontext.get(0))));


        sa=String.valueOf(a);
        sb=String.valueOf(b);
        sc=String.valueOf(c);
        sd=String.valueOf(d);
        se=String.valueOf(e);
        sf=String.valueOf(f);


    }

    @Override
    public void onClick(View v) {
        if(numofClick<=5) {
            if (((Button) v).getText() == sa) {
                xp+=10;
                pointxt.setText((getString(R.string.xptext))+xp);

                numofClick++;

            } else {
                point -= 10;
                numofClick++;
            }
        }
        else if(numofClick>=5 ) {
            b1.setVisibility(View.INVISIBLE);
            b2.setVisibility(View.INVISIBLE);
            b3.setVisibility(View.INVISIBLE);
            b4.setVisibility(View.INVISIBLE);
            b5.setVisibility(View.INVISIBLE);
            b6.setVisibility(View.INVISIBLE);
            answer_text.setVisibility(View.VISIBLE);
            sonuc =answer_text.getText().toString().trim();
            choose.setText(getString(R.string.enter));

            if(sonuc.equals(sa)){
                xp+=15;
                pointxt.setText((getString(R.string.xptext))+xp);
                numofClick++;
            }
            else{
                point-=10;
                numofClick++;
            }


        }
/// game over ekranı kısmı
        if(point==0 ||(numofClick==10)) {
            Intent gameover= new Intent(this,GameFinished.class);
            gameover.putExtra("xp",xp);
            startActivity(gameover);

        }
        setRandomDigit();
        answer_text.setText("");
        if(point<0){
            point=0;
        }
        else if(point>max_points){
            point=max_points;
        }
        pointxt.setText(getString(R.string.xptext)+xp);
        int progress=(int)((point/(float)max_points)*50);
        progressBar.setProgress(point);

    }

}