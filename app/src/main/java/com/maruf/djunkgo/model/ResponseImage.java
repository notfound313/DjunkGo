package com.maruf.djunkgo.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class ResponseImage {
    @SerializedName("img")
    String img;


    private Boolean isDapatDijual;
    private Boolean isAnorganik;
    private String result;
    private String probability;
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Boolean getIsDapatDijual() {
        return isDapatDijual;
    }

    public void setIsDapatDijual(Boolean isDapatDijual) {
        this.isDapatDijual = isDapatDijual;
    }

    public Boolean getIsAnorganik() {
        return isAnorganik;
    }

    public void setIsAnorganik(Boolean isAnorganik) {
        this.isAnorganik = isAnorganik;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getProbability() {
        return probability;
    }

    public void setProbability(String probability) {
        this.probability = probability;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}