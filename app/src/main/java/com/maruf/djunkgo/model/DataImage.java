package com.maruf.djunkgo.model;


import java.util.HashMap;
import java.util.Map;

public class DataImage {
    private ResponseImage data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ResponseImage getData() {
        return data;
    }

    public void setData(ResponseImage data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }


}
