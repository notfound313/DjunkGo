package com.maruf.djunkgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerifySuccessActivity extends AppCompatActivity {
    private Button tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_success);

        tv_login = findViewById(R.id.btn_masuk);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(VerifySuccessActivity.this, LoginActivity.class);
                startActivity(login);
                finish();

            }
        });
    }
}