package com.maruf.djunkgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.maruf.djunkgo.javaClass.SessionManger;

public class WelcomeActivity extends AppCompatActivity {
    public Button btn_register, btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btn_register=findViewById(R.id.btn_daftar);
        btn_login= findViewById(R.id.btn_masuk);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(login);
                finish();
            }
        });






        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        SessionManger sessionManger = new SessionManger(this);
        if(sessionManger.isLogin() == true){
            Intent register = new Intent(WelcomeActivity.this, MainActivity.class);
            startActivity(register);
            finish();
        }
    }
}