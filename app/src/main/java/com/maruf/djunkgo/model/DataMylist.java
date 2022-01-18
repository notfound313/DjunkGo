package com.maruf.djunkgo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataMylist {
    @SerializedName("data")
    @Expose

   private ResponseMylist[] mylist;
    public ResponseMylist[] getMylist(){
        return mylist;
    }

}
