package com.maruf.djunkgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.maruf.djunkgo.Api.ApiInterface;
import com.maruf.djunkgo.Api.RetroClient;
import com.maruf.djunkgo.adapter.MylistAdapter;
import com.maruf.djunkgo.javaClass.SessionManger;
import com.maruf.djunkgo.model.DataImage;
import com.maruf.djunkgo.model.DataMylist;
import com.maruf.djunkgo.model.ResponseDasboard;
import com.maruf.djunkgo.model.ResponseMylist;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MylistActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MylistAdapter mylistAdapter;
    ArrayList<ResponseMylist> mylists ;
    SessionManger sessionManger;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);
        recyclerView = findViewById(R.id.datalist);

        //session recleview
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        //sessiondialogprogress
        progressDialog = new ProgressDialog(MylistActivity.this);
        progressDialog.setMessage("wait...");

        //session
        sessionManger = new SessionManger(this);
        sessionManger.chekLogin();

        progressDialog.show();


        ShowMylist();



    }

    private void ShowMylist() {
        HashMap<String, String> userdetail = sessionManger.getUserDetailFromSession();
        String get_token = userdetail.get(sessionManger.TOKENJWT);
        ApiInterface apiInterface = RetroClient.getRetrofitIntance().create(ApiInterface.class);
        Call<DataMylist> call = apiInterface.getResponseMylist("Bearer " + get_token);
        call.enqueue(new Callback<DataMylist>() {
            @Override
            public void onResponse(Call<DataMylist> call, Response<DataMylist> response) {

                if(response.isSuccessful()){
                    if(response.code()==200){
                        DataMylist body = response.body();
                        mylists = new ArrayList<>(Arrays.asList(body.getMylist()));
                        mylistAdapter = new MylistAdapter(mylists, MylistActivity.this);
                        recyclerView.setAdapter(mylistAdapter);
                        mylistAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();




                    }else {
                        Toast.makeText(MylistActivity.this, "gagal memuat data"+response.body().toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(MylistActivity.this, "gagal loading data"+response.body().toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }




            }

            @Override
            public void onFailure(Call<DataMylist> call, Throwable t) {
                Log.e("TAG", t.toString());
                t.printStackTrace();
                Toast.makeText(MylistActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }

}
