package com.maruf.djunkgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.maruf.djunkgo.Api.ApiInterface;
import com.maruf.djunkgo.Api.RetroClient;
import com.maruf.djunkgo.javaClass.FileUtils;
import com.maruf.djunkgo.javaClass.SessionManger;
import com.maruf.djunkgo.model.DataImage;
import com.maruf.djunkgo.model.ResponseDasboard;
import com.maruf.djunkgo.model.ResponseImage;
import com.maruf.djunkgo.model.ResponseLogin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanImageActivity extends AppCompatActivity {

    Uri image_uri;
    private Button takePicture, scanImage;
    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE =1001;
    private ImageView imagejunk;
    private JSONObject jsonObject;
    private RequestQueue requestQueue;
    private static String URL_IMG="http://192.168.19.114:5000/scan-img";
    SessionManger sessionManger;
    private ProgressDialog progressDialog;
    TextView deteksi,cansale1,isorganik1,probality1,pesan1;
    private String SelectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_image2);

        takePicture =findViewById(R.id.takepicture);
        imagejunk = findViewById(R.id.imagescan);
        progressDialog = new ProgressDialog(ScanImageActivity.this);
        progressDialog.setMessage("Scanning...");
        scanImage= findViewById(R.id.scanimage);
        deteksi = findViewById(R.id.deteksi);
        cansale1 = findViewById(R.id.cansale);
        isorganik1 = findViewById(R.id.isorganik);
        probality1 = findViewById(R.id.probality);
        pesan1 = findViewById(R.id.pesan);
        pesan1.setVisibility(View.GONE);
        isorganik1.setVisibility(View.GONE);
        cansale1.setVisibility(View.GONE);


        //session
        sessionManger = new SessionManger(this);
        sessionManger.chekLogin();


        //logout
        scanImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManger.logout();
            }
        });




        //takepicture
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if ( checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED  ){
                        //permission not enebled
                        String [] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
                        //popup
                        requestPermissions(permission, PERMISSION_CODE);
                    }else{
                        openCamera();
                    }
                }else{
                    openCamera();
                }
            }
        });



    }

    private void openCamera() {
        //camera
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraintent, 1);
    }
    //handling permision

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(),image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension,dimension);
            imagejunk.setImageBitmap(image);
            SelectedImage = FileUtils.getPath(ScanImageActivity.this, getImageUri(ScanImageActivity.this, image));
            progressDialog.show();
            UploadImage(image);
        }
        super.onActivityResult(requestCode, resultCode, data);
        
    }

    private Uri getImageUri(Context context, Bitmap bitmap ) {
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"myPicture","");
        return Uri.parse(path);
    }

    private void UploadImage(Bitmap bitmap) {
        HashMap<String,String> userdetail= sessionManger.getUserDetailFromSession();
        String get_token= userdetail.get(sessionManger.TOKENJWT);
        File file= new File(Uri.parse(SelectedImage).getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part filepart = MultipartBody.Part.createFormData("img", file.getName(),requestBody);

        ApiInterface apiInterface = RetroClient.getRetrofitIntance().create(ApiInterface.class);
        Call<DataImage> call =  apiInterface.getuploadImage(filepart,"Bearer "+get_token);
        call.enqueue(new Callback<DataImage>() {
            @Override
            public void onResponse(Call<DataImage> call, Response<DataImage> response) {
                if(response.isSuccessful()){
                    if(response.code() == 200){

                        String result = response.body().getData().getResult();
                        String probality = response.body().getData().getProbability().toString();

                        deteksi.setText(result);
                        probality1.setText("Probality : "+probality);

                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(ScanImageActivity.this, "gagal "+response.toString(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(ScanImageActivity.this, "upload gagal "+response.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<DataImage> call, Throwable throwable) {
                Log.e("TAG", throwable.toString());
                throwable.printStackTrace();
                Toast.makeText(ScanImageActivity.this, throwable.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });


    }

}