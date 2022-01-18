package com.maruf.djunkgo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class ResponseMylist {
    private Integer id;
    @SerializedName("nama_item")
    @Expose
    private String namaItem;
    @SerializedName("jumlah")
    @Expose
    private String jumlah;
    @SerializedName("jenis")
    @Expose
    private String jenis;
    @SerializedName("url_delete")
    @Expose
    private String urlDelete;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ResponseMylist(Integer id, String namaItem, String jumlah, String jenis, String urlDelete) {
        this.id = id;
        this.namaItem = namaItem;
        this.jumlah = jumlah;
        this.jenis = jenis;
        this.urlDelete = urlDelete;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaItem() {
        return namaItem;
    }

    public void setNamaItem(String namaItem) {
        this.namaItem = namaItem;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getUrlDelete() {
        return urlDelete;
    }

    public void setUrlDelete(String urlDelete) {
        this.urlDelete = urlDelete;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
