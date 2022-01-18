package com.maruf.djunkgo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.maruf.djunkgo.Api.ApiInterface;
import com.maruf.djunkgo.Api.RetroClient;
import com.maruf.djunkgo.javaClass.SessionManger;
import com.maruf.djunkgo.model.ResponseLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private EditText Email;
    private TextInputEditText password;
    private TextView forget;
    private Button btn_login;
    private ProgressBar loading;
    SessionManger sessionManger;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManger = new SessionManger(this);

        Email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password1);
        forget = findViewById(R.id.btn_lupa);
        btn_login= findViewById(R.id.btn_masuk);
        loading= findViewById(R.id.progres);





        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
            }

        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                loading.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);


                if(!email.isEmpty() && !pass.isEmpty()){
                    btnLoginUser(email,pass);
                }else{
                    Email.setError("email kosong");
                    password.setError("password kosong");
                    loading.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void btnLoginUser(String email, String password) {
        ApiInterface apiInterface = RetroClient.getRetrofitIntance().create(ApiInterface.class);
        Call<ResponseLogin> call =  apiInterface.getLoginInformation(email,password);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, retrofit2.Response<ResponseLogin> response) {
                if(response.isSuccessful()) {
                    if (response.code() == 200) {
                        String token = response.body().getToken();
                        sessionManger.createLogInSession(email, password, token);
                        Intent i = null;
                        i = new Intent(LoginActivity.this, MainActivity.class);
                        i.putExtra("email", email);
                        i.putExtra("password", password);
                        i.putExtra("token", token);
                        startActivity(i);
                        finish();

                    }else{
                        Toast.makeText(LoginActivity.this, "salah mengkonfirmasi akun", Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this, "response gagal", Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_login.setVisibility(View.VISIBLE);
                    
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable throwable) {
                Log.e("TAG", throwable.toString());
                throwable.printStackTrace();
                Toast.makeText(LoginActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);



            }
        });

    }


}