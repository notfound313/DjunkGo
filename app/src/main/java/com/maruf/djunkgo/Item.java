package com.maruf.djunkgo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.maruf.djunkgo.Api.ApiInterface;
import com.maruf.djunkgo.Api.RetroClient;
import com.maruf.djunkgo.javaClass.SessionManger;
import com.maruf.djunkgo.model.DataMylist;
import com.maruf.djunkgo.model.ResponseItem;
import com.maruf.djunkgo.model.ResponseMessage;
import com.maruf.djunkgo.model.ResponseMylist;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Item extends AppCompatActivity {

    private TextView smph,jns,jmlh;
    private ImageView delete;
    SessionManger sessionManger;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        smph = findViewById(R.id.getitem);
        jns = findViewById(R.id.jenis);
        jmlh = findViewById(R.id.jumlah);
        delete= findViewById(R.id.delete);
        progressDialog = new ProgressDialog(Item.this);
        progressDialog.setMessage("wait...");

        progressDialog.show();



        sessionManger = new SessionManger(this);
        sessionManger.chekLogin();


        showDetail();


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(Item.this);
                builder.setPositiveButton("ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String id = getIntent().getStringExtra("id");
                        HashMap<String,String> userdetail= sessionManger.getUserDetailFromSession();
                        String get_token= userdetail.get(sessionManger.TOKENJWT);
                        ApiInterface apiInterface = RetroClient.getRetrofitIntance().create(ApiInterface.class);
                        Call<ResponseMessage> call =  apiInterface.getMessageResponse(id,"Bearer "+get_token);
                        call.enqueue(new Callback<ResponseMessage>() {
                            @Override
                            public void onResponse(Call<ResponseMessage> call, Response<ResponseMessage> response) {
                                if(response.isSuccessful()){
                                    String message = response.body().getMessage();
                                    progressDialog.dismiss();
                                    Toast.makeText(Item.this, message, Toast.LENGTH_SHORT).show();
                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(Item.this, "gagal mengahapus", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseMessage> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(Item.this, "gagal mengahapus "+ t.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }).setNegativeButton("tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Apakah ingin di hapus?");
                builder.show();


            }
        });

    }

    private void showDetail() {
        String id = getIntent().getStringExtra("id");
        HashMap<String, String> userdetail = sessionManger.getUserDetailFromSession();
        String get_token = userdetail.get(sessionManger.TOKENJWT);
        ApiInterface apiInterface = RetroClient.getRetrofitIntance().create(ApiInterface.class);
        Call<ResponseItem> call = apiInterface.getDataList(id,"Bearer " + get_token);
        call.enqueue(new Callback<ResponseItem>() {
            @Override
            public void onResponse(Call<ResponseItem> call, Response<ResponseItem> response) {

                if(response.isSuccessful()){
                    if(response.code()==200){
                        String jenis = response.body().getDataItem().getJenis();
                        String jumlah = response.body().getDataItem().getJumlah();
                        String nama = getIntent().getStringExtra("nama");

                        smph.setText(nama);
                        jmlh.setText(jumlah);
                        jns.setText(jenis);

                        progressDialog.dismiss();


                    }else {
                        Toast.makeText(Item.this, "gagal memuat data", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(Item.this, "gagal loading data", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }




            }

            @Override
            public void onFailure(Call<ResponseItem> call, Throwable t) {
                Log.e("TAG", t.toString());
                t.printStackTrace();
                Toast.makeText(Item.this, t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }

}