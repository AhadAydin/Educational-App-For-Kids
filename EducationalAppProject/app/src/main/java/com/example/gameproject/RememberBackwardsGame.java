package com.example.gameproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

public class RememberBackwardsGame extends AppCompatActivity {

    ImageView digitImg; TextView digitsText; // digitImg = rakamların göründüği imgView, digitsText : kullanıcının girdiği rakamları gösteren textView
    int[] digitArr, enteredDigitArr;// digitArr : seçilmiş sayılar, enteredDigitArr : kullanıcının bastığı sayılar
    boolean isButtonsActive = false; // butonları aktif etmeye yarıo
    int level = 2,enteredDigitsCount=0,questionCount = 0,levelup_count = 0,correctAnswerCount=0;  // level:zorluk derecesi, enteredDigitsCount: kullanıcının aynı soruda girdiği rakam sayısı, questionCount = cevaplanan soru sayısı, levelUp_count: bu da soruları sayıyo ama level atlayınca sıfırlanıo
    int MAX_QUESTION=9, LEVELUP_AMOUNT = 3; // max soru sayısı ve level atlamak için gereken soru sayısı
    int MAX_ENERGY = 3,currentEnergy=3;
    Handler handler = new Handler();
    TextView gameInfo; ProgressBar progressBar;EnergyBar energyBar; // gameInfo : yukarda mesaj yazan beyaz kutu
    Animation shakeAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_backwards_game);

        digitImg = findViewById(R.id.rememberBackwards_imageView);
        digitsText = findViewById(R.id.enteredDigitsBackwards_text);

        gameInfo = findViewById(R.id.remember_backwards_msg);
        progressBar = findViewById(R.id.remember_backwards_enerygBar);
        energyBar = new EnergyBar(progressBar,MAX_ENERGY);
        shakeAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.wrong_answer_anim);

        digitsText.setText("");

        startAsking(1000); // sormaya başlıyo(line 150)
    }

    public void onDigitBackwardsButtonClick(View view){ // bir rakam butonu tıklandığında
        if(isButtonsActive){ // eğer butonlar aktifse
            if(enteredDigitsCount==0)  //////////////// enteredDigitsCount = 0 ise bu daha soruya cevap vermeye başlanmamış demek o zaman enteredDigitArr'ı level neyse o kadar uzun yapıyo
                enteredDigitArr = new int[level];
            int buttonDigit = getDigitFromButton(view.getId()); // hangi butona basıldığını öğrenmek için lien 85 deki fonksiyonu çağırıyo ve buttonDigit e eşitlio
            if(buttonDigit!=-1){ // yanlış butona basılmadığından emin olmak için yaptım
                enteredDigitArr[enteredDigitsCount] = buttonDigit; // kullanıcı kaçıncı rakamı yazıosa o indexe yazdığı rakamı koyuyo
                enteredDigitsCount++;  // kullanıcı bir rakam girdi o yüzden girilen rakam miktarını arttırmak lazm
                showEnteredDigits(); // ekranda kullanıcın bastığı rakamlar görünsün die yaptığım bi fonksiyon(line 58)
                if(enteredDigitsCount == level){ // girilen rakamlar level kadar olduysa bu demektir ki kullanıcı cevabını tamamladı. Kontrol etmeliyiz
                    checkAnswer(); // line 65
                }
            }
        }
    }
    private void showEnteredDigits(){
        String finalStr = ""; // boş bi string yaratıyo
        for(int i = 0;i<enteredDigitsCount;i++){ // girilen rakam miktarı kadar for döngüsü yapıyo
            finalStr += enteredDigitArr[i] + " "; // finalStr'ye kullanıcının girmiş olduğu rakamı yazıyo yanına da bi boşluk atıyo
        }
        digitsText.setText(finalStr); // ve stryi gösteriyo
    }
    private void checkAnswer(){

        boolean answer = true;
        for(int i = 0;i<digitArr.length;i++){ // belirlenen rakamların arrayinin uzunluğu kadar for döngüsü
            if(digitArr[i] != enteredDigitArr [digitArr.length -i -1]){ // belirlenen sayıyla kullanıcının sayısı aynı mı kontrol edio deilse answer = false diyo ve döngüden çıkıyo
                answer=false;
                break;
            }
        }
        Log.d("answer" , String.valueOf(answer));
        if(answer){
            gameInfo.setText(R.string.correct_answer); // mesaj kutusunu ayarlıyo
            correctAnswerCount++;
        }else{
            digitsText.startAnimation(shakeAnim);
            energyBar.addEnergy(-1);
            gameInfo.setText(R.string.wrong_answer); // mesaj kutusunu ayarlıyo
        }
        // check levelup
        if(levelup_count>=LEVELUP_AMOUNT){ // level arttırcak kadar soru cevaplanmış mı kontrol ediyo
            level++;
            levelup_count = 0;
        }

        if(questionCount >= MAX_QUESTION){ // max soru sayısına ulaşılmış mı kontrol edio
            digitImg.setImageDrawable(getDrawable(R.drawable.soho_happy));
            loadGameFinishedScene();
        }else if(energyBar.isZero()){ // enerji bitti mi die kontrol edio
            gameInfo.setText(R.string.out_of_energy);
            loadGameFinishedScene();
        }else{
            // reset scene          // Sahneyi resetleyip yeni soru sormamız lazım, girilmiş rakam sayısını sıfırlıyo ve butonları kapatıo
            enteredDigitsCount = 0;
            isButtonsActive = false;
            startAsking(400);
        }
    }

    private int getDigitFromButton(int id){
        // id list goes like that : button_digit0,button_digit1,button_digit2...
        // therefore if we subtract button_digit9(example id) - button_digit0 we get 9
        Log.d("getDigit",String.valueOf(id-R.id.buttonBackwards_digit0));
        return id-R.id.buttonBackwards_digit0;
    }

    private void setDigits(){
        digitArr = new int[level]; // digitArr arrayini hangi leveldeysek o kadar büyüklükte initialize ediyo
        for(int i = 0;i<digitArr.length;i++){
            digitArr[i] = new Random().nextInt(10); // arrayin her elemanına random bi rakam atıyoz
        }
    }

    private void showDigits(int index){ // bu fonksiyon kendini çağıran bi fonksiyon o yüzden index deişkeni lazım, her çalıştığında
        // index 1 artacak ve level'a(gösterilecek rakam sayısı) eşit olduğunda duruyo.

        digitsText.setText(""); // kullanıcı daha bişi girmemiş olacak o yüzden boş yapıoz

        Runnable showNextRunnable = new Runnable() { // sıradaki rakamı göstermek için kendi fonksiyonunu yani showDigitsi tekrar çağırıyo ama bu sefer indexi bi fazla gönderiyo
            @Override
            public void run() {
                showDigits(index+1);
            }
        };
        Runnable runnable=new Runnable() { // rakamı göstermeyi bırakıyo ve 200 ms sonra showNextRunnable'ı(line 120) çağırıyo
            @Override
            public void run() {
                digitImg.setImageDrawable(getDrawable(R.drawable.empty_drawable)); // visiblity'sini invisible yapmak yerine boş resim koyduruorum ki imgView'ın bakcgroundu kaybolmasın sadece rakam kaybolsun
                handler.postDelayed(showNextRunnable,200);
            }
        };

        if(index==level){ // bütün rakamları göstermiş olduğu için soru sayaçlarını arttırıyo ve butonları aktive ediyo. Return diyerek fonksiyonu bitiro
            questionCount++;
            levelup_count++;
            Log.d("index" , String.valueOf(index));
            digitImg.setImageDrawable(getDrawable(R.drawable.soho_cruious));
            isButtonsActive=true;
            return;
        }else{ // show digit    // henüz tüm sayılar gösterilmemiş çünkü index < level. O zaman digitArr[index]'i gösterio ve runnable'ı(lien 1010) yarım saniye sonra çağıtıro
            String imageName = "digit_" +digitArr[index];
            int resourceId=getResources().getIdentifier(imageName,"drawable",getPackageName());
            digitImg.setImageResource(resourceId);

            handler.postDelayed(runnable,500);
        }
    }

    private void startAsking(int delay){
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                gameInfo.setText(R.string.remember_digits_info); // mesajı ayarlıyo
                showDigits(0);
            } // rakamları gösteren fonksiyonu çağırıyo
        };

        setDigits(); // gösterilecek sayıları belirliyo
        handler.postDelayed(runnable,delay);// 1 saniye beklio ( oyun açılır açılmaz başlamasın diye)
    }

    private void loadGameFinishedScene(){
        Intent intent = new Intent(this,GameFinished.class);
        int xp = Math.round((float)correctAnswerCount/MAX_QUESTION * 100);
        intent.putExtra("xp",xp);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        handler.postDelayed(runnable,800);
    }

}