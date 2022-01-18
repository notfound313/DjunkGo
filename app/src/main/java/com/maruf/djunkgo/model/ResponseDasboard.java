package com.maruf.djunkgo.model;

public class ResponseDasboard {
    private  String total_item;
    private  String organik;
    private  String anorganik;
    private  String terjual;

    public ResponseDasboard(String total_item, String organik, String anorganik, String terjual) {
        this.total_item = total_item;
        this.organik = organik;
        this.anorganik = anorganik;
        this.terjual = terjual;
    }

    public String getTotal_item() {
        return total_item;
    }

    public void setTotal_organik(String total_organik) {
        this.total_item = total_item;
    }

    public String getOrganik() {
        return organik;
    }

    public void setOrganik(String organik) {
        this.organik = organik;
    }

    public String getAnorganik() {
        return anorganik;
    }

    public void setAnorganik(String anorganik) {
        this.anorganik = anorganik;
    }

    public String getTerjual() {
        return terjual;
    }

    public void setTerjual(String terjual) {
        this.terjual = terjual;
    }
}
