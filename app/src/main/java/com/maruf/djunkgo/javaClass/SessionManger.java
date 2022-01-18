package com.maruf.djunkgo.javaClass;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.maruf.djunkgo.MainActivity;
import com.maruf.djunkgo.WelcomeActivity;

import java.util.HashMap;

public class SessionManger {

    SharedPreferences sharedPreferences;
    public  SharedPreferences.Editor editor;
    Context context;

    private static final String IS_LOGIN ="isLoggedIn";
    private static final String PREF_NAME ="login";

    public static final String EMAIL = "email";
    public static final String PASSWORD = "pasword";
    public static final String TOKENJWT = "token";

    public SessionManger(Context _context){
        context = _context;
        sharedPreferences = context.getSharedPreferences("userLogInSession", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
    }
    public  void createLogInSession(String email, String password, String token) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.putString(TOKENJWT, token);
        editor.apply();
    }
    public HashMap<String,String> getUserDetailFromSession(){
        HashMap<String,String> userData = new HashMap<>();

        userData.put(EMAIL, sharedPreferences.getString(EMAIL,null));
        userData.put(PASSWORD, sharedPreferences.getString(PASSWORD,null));
        userData.put(TOKENJWT, sharedPreferences.getString(TOKENJWT,null));
        return userData;
    }
    public Boolean isLogin(){
        return  sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public boolean chekLogin() {
        if (!this.isLogin()) {
            Intent i = new Intent(context, WelcomeActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
            return false;

        }else{
            return true;
        }


    }
    public  void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, WelcomeActivity.class);
        context.startActivity(i);
        ((MainActivity) context).finish();
    }
}


