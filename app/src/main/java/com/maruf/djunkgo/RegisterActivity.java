package com.maruf.djunkgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.VolumeProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.maruf.djunkgo.Api.ApiInterface;
import com.maruf.djunkgo.Api.RetroClient;
import com.maruf.djunkgo.model.ResponseLogin;
import com.maruf.djunkgo.model.ResponseRegister;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {

    private EditText Ednama,Edemail;
    Button submit_reg;
    TextView tv_login;
    TextInputEditText Edpassword,repass;
    public ProgressBar progres;
    private static String URL_REG = "http://192.168.19.114:5000/auth/register";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Ednama= findViewById(R.id.edt_nama);
        Edemail= findViewById(R.id.edt_email);
        Edpassword= findViewById(R.id.password);
        repass = findViewById(R.id.repassword);
        submit_reg= findViewById(R.id.btn_daftar);
        tv_login = findViewById(R.id.btn_masuk);
        progres=findViewById(R.id.progres);


        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
               startActivity(login);
               finish();

            }
        });



        submit_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = Ednama.getText().toString().trim();
                String email= Edemail.getText().toString().trim();
                String pass= Edpassword.getText().toString().trim();
                String cpass= repass.getText().toString().trim();
                progres.setVisibility(View.VISIBLE);
                submit_reg.setVisibility(View.GONE);

                if(!nama.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !cpass.isEmpty()){
                    if(pass.equals(cpass)){
                        cekPass();
                    }else{
                        repass.setError("konfirmasi password tidak cocok");
                        progres.setVisibility(View.GONE);
                        submit_reg.setVisibility(View.VISIBLE);
                    }
                }else{
                    Ednama.setError("masukan nama");
                    Edemail.setError("masukan email");
                    Edpassword.setError("masukan password");
                    repass.setError("masukan konfirmasi password");
                    progres.setVisibility(View.GONE);
                    submit_reg.setVisibility(View.VISIBLE);
                }

            }
        });




    }

    private  void cekPass(){
        String nama = Ednama.getText().toString().trim();
        String email= Edemail.getText().toString().trim();
        String password1 = Edpassword.getText().toString();
        String password2 = repass.getText().toString();        

        if(!(password1.length() >= 6)){
            Edpassword.setError("minimal password 6 karakter");
            progres.setVisibility(View.GONE);
            submit_reg.setVisibility(View.VISIBLE);
           
        }else if(password1.contains(" ")){           
            Edpassword.setError("password sebaiknya tidak ada spasi");
            progres.setVisibility(View.GONE);
            submit_reg.setVisibility(View.VISIBLE);
        }else if (!(password1.contains("@") || password1.contains("#")
                || password1.contains("!") || password1.contains("~")
                || password1.contains("$") || password1.contains("%")
                || password1.contains("^") || password1.contains("&")
                || password1.contains("*") || password1.contains("(")
                || password1.contains(")") || password1.contains("-")
                || password1.contains("+") || password1.contains("/")
                || password1.contains(":") || password1.contains(".")
                || password1.contains(", ") || password1.contains("<")
                || password1.contains(">") || password1.contains("?")
                || password1.contains("|"))) {
           
            Edpassword.setError("setidaknya ada satu karakter spesial");
            progres.setVisibility(View.GONE);
            submit_reg.setVisibility(View.VISIBLE);


        }

        else{
            if (password1 == null ? password2 != null : !password1.equals(password2)){
                
                repass.setError("Password tidak cocok");
                progres.setVisibility(View.GONE);
                submit_reg.setVisibility(View.VISIBLE);
            }else{

                creteUser(nama,email,password1);
            }

        }



    }

    private void creteUser(String nama,String email, String  password) {
        ApiInterface apiInterface = RetroClient.getRetrofitIntance().create(ApiInterface.class);
        Call<ResponseRegister> call =  apiInterface.getRegister(nama,email,password);
        call.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, retrofit2.Response<ResponseRegister> response) {
                if(response.code()==201){
                    String message = response.body().getMessage();
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(RegisterActivity.this, VerifySuccessActivity.class);
                    finish();
                    startActivity(intent);
                }else if(response.code()==200){
                    String message = response.body().getMessage();
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                }else{
                    String message = response.body().getMessage();
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Registrasi gagal coba lagi", Toast.LENGTH_SHORT).show();

            }
        });
    }





}