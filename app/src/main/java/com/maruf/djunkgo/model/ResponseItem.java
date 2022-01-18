package com.maruf.djunkgo.model;

import java.util.HashMap;
import java.util.Map;

public class ResponseItem {
    private ResponseMylist data;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ResponseMylist getDataItem() {
        return data;
    }

    public void setDataItem(ResponseMylist data) {
        this.data = data;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
