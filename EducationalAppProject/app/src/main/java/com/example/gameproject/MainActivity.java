package com.example.gameproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    CardView loginCard,signUpcard;
    Button loginButton,signUpButton,switchButton;
    TextView warningText;
    EditText loginEmail,loginPassword,SignUpUsername,signUpEmail,signUpPassword,SignUpPasswordAgain;

    private String currentUsername;
    private boolean isLogin = true;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        auth=FirebaseAuth.getInstance();

        loginCard=findViewById(R.id.loginCard);
        signUpcard=findViewById(R.id.signUpCard);
        warningText=findViewById(R.id.warning_text);
        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.signUp_button);
        switchButton = findViewById(R.id.switch_button);
        loginEmail = findViewById(R.id.login_email_text);
        loginPassword = findViewById(R.id.login_password_text);
        SignUpUsername = findViewById(R.id.signUp_username_text);
        signUpEmail=findViewById(R.id.signUp_email_text);
        signUpPassword = findViewById(R.id.signUp_password_text);
        SignUpPasswordAgain = findViewById(R.id.signUp_password2_text);

    }

    public void OnLoginOrSignUpButtonClick(View view){

        if(isLogin){ // Login
            String log_email = loginEmail.getText().toString();
            String log_password = loginPassword.getText().toString();

            loginUser(log_email,log_password);

        }else{ // Sign up
            String reg_username = SignUpUsername.getText().toString();
            String reg_email = signUpEmail.getText().toString();
            String reg_password = signUpPassword.getText().toString();
            String reg_password2= SignUpPasswordAgain.getText().toString();

            if(TextUtils.isEmpty(reg_username)||TextUtils.isEmpty(reg_password) ||TextUtils.isEmpty(reg_password2) || TextUtils.isEmpty(reg_email)){
                warningText.setVisibility(View.VISIBLE);
                warningText.setText(R.string.empty_username_password);
            }else if( reg_username.length()<4){
                warningText.setVisibility(View.VISIBLE);
                warningText.setText(R.string.username_less_char);
            }else if(reg_password.length()<6){
                warningText.setVisibility(View.VISIBLE);
                warningText.setText(R.string.password_less_char);
            }else if(!reg_password.equals(reg_password2)){
                warningText.setVisibility(View.VISIBLE);
                warningText.setText(R.string.passwords_dont_match);
            }else{
                currentUsername = reg_username;
                registerUser(reg_email,reg_password);
            }
        }
    }

    public void OnSwitchRegisterButtonClick(View view){
        if(isLogin){
            isLogin=false;
            switchButton.setText(R.string.ask_login);
            loginCard.setVisibility(View.INVISIBLE);
            signUpcard.setVisibility(View.VISIBLE);
        }else{
            isLogin=true;
            switchButton.setText(R.string.ask_register);
            loginCard.setVisibility(View.VISIBLE);
            signUpcard.setVisibility(View.INVISIBLE);
        }
    }


    private void registerUser(String username,String password){
        auth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(this  , new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d("db",String.valueOf(auth.getCurrentUser().zzb()));
                    initializeUserData();
                    loadMainMenu();
                }else{
                    warningText.setVisibility(View.VISIBLE);
                    warningText.setText(R.string.invalid_email);
                }
            }
        });
    }

    private void loginUser(String email,String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    warningText.setVisibility(View.VISIBLE);
                    warningText.setText(R.string.entering_to_app);
                    Log.d("db",auth.getCurrentUser().toString());
                    loadMainMenu();
                }else{
                    warningText.setVisibility(View.VISIBLE);
                    warningText.setText(R.string.wrong_password);
                }
            }
        });
    }

    private void initializeUserData(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> data = new HashMap<>();
        data.put("username",currentUsername);
        data.put("xp",0);

        db.collection("userData").document(auth.getCurrentUser().getUid()).set(data, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    warningText.setVisibility(View.VISIBLE);
                    warningText.setText("data saved");
                }
            }
        });
    }

    private void loadMainMenu(){
        Log.d("db",auth.getCurrentUser().getUid());
        Intent intent = new Intent(this,MainMenu.class);
        startActivity(intent);
        finish();
    }

}