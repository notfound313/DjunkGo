package com.maruf.djunkgo.javaClass;

import android.app.Application;
import android.graphics.Typeface;

public class font extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypeFace.overrideFont(getApplicationContext(), "SERIF","font/Montserrat_Light.ttf");
    }
}
