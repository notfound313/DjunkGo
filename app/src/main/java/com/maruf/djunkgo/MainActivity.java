package com.maruf.djunkgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maruf.djunkgo.Api.ApiInterface;
import com.maruf.djunkgo.Api.RetroClient;
import com.maruf.djunkgo.javaClass.SessionManger;
import com.maruf.djunkgo.model.ResponseDasboard;
import com.maruf.djunkgo.model.ResponseLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    public ImageView partners, myprofil,myitem, mylists;
    public RelativeLayout mtakePicture;
    private ProgressDialog progressDialog;


    private long exitTime = 0;
    private TextView tv_total,tv_organik,tv_anorganik,tv_terjual;
    private static String URL_DASH= "http://192.168.19.114:5000/dashboard";


    SessionManger sessionManger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        partners= findViewById(R.id.partners);
        myprofil= findViewById(R.id.myprofil);
        myitem= findViewById(R.id.myitems);
        mylists= findViewById(R.id.mylist);
        tv_total = findViewById(R.id.total);
        tv_organik = findViewById(R.id.card1);
        tv_anorganik = findViewById(R.id.card2);
        tv_terjual = findViewById(R.id.card3);
        mtakePicture= findViewById(R.id.mTakePicture);

        //parse


        //session
        sessionManger = new SessionManger(this);
        sessionManger.chekLogin();

        //proses
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("wait..");





        //fungsi-fungsi class
        onBackPressed();
        //dashboard
        progressDialog.show();
        ViewDasboard();






        //setOnclik
        mylists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MylistActivity.class));

            }
        });

        myprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), myprofil.class));
            }
        });

        partners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),mitra.class));
            }
        });

        myitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), myitems.class));
            }
        });

        //takepicture
        mtakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //scan image
                startActivity(new Intent(MainActivity.this, ScanImageActivity.class));
            }
        });
    }

    private void ViewDasboard() {
        HashMap<String,String> userdetail= sessionManger.getUserDetailFromSession();
        String get_token= userdetail.get(sessionManger.TOKENJWT);
        ApiInterface apiInterface = RetroClient.getRetrofitIntance().create(ApiInterface.class);
        Call<ResponseDasboard> call =  apiInterface.getResponseDashboard("Bearer "+get_token);

        call.enqueue(new Callback<ResponseDasboard>() {
            @Override
            public void onResponse(Call<ResponseDasboard> call, retrofit2.Response<ResponseDasboard> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Code "+ response.code(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }else{
                    String total_itm = response.body().getTotal_item();
                    String organik = response.body().getOrganik();
                    String anorganik = response.body().getAnorganik();
                    String terjual = response.body().getTerjual();
                    tv_total.setText(total_itm);
                    tv_anorganik.setText(anorganik);
                    tv_terjual.setText(terjual);
                    tv_organik.setText(organik);
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<ResponseDasboard> call, Throwable throwable) {
                Log.e("Tag", throwable.toString());
                progressDialog.dismiss();

            }
        });


    }


    /// private class

    //fungsi daouble tap
    public void onBackPressed() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }


    }

}