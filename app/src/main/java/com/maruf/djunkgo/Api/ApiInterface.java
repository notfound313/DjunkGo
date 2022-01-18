package com.maruf.djunkgo.Api;

import android.media.Image;

import com.maruf.djunkgo.model.DataImage;
import com.maruf.djunkgo.model.DataItem;
import com.maruf.djunkgo.model.DataMylist;
import com.maruf.djunkgo.model.ResponseDasboard;
import com.maruf.djunkgo.model.ResponseImage;
import com.maruf.djunkgo.model.ResponseItem;
import com.maruf.djunkgo.model.ResponseLogin;
import com.maruf.djunkgo.model.ResponseMessage;
import com.maruf.djunkgo.model.ResponseMylist;
import com.maruf.djunkgo.model.ResponseRegister;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseLogin> getLoginInformation(@Field("email") String Email, @Field("password") String Password);

    @FormUrlEncoded
    @POST("auth/register")
    Call<ResponseRegister> getRegister(@Field("nama") String Nama,@Field("email") String Email, @Field("password") String Password);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("dashboard")
    Call<ResponseDasboard> getResponseDashboard(@Header("Authorization") String auth);


    @Multipart
    @POST("scan-img")
    Call<DataImage> getuploadImage(@Part MultipartBody.Part image, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("list-saya")
    Call<DataMylist> getResponseMylist(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("list-saya/{id}")
    Call<ResponseMessage> getMessageResponse(@Path ("id")String id,@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("list-saya/{id}")
    Call<ResponseItem> getDataList(@Path ("id")String id, @Header("Authorization") String auth);
}
