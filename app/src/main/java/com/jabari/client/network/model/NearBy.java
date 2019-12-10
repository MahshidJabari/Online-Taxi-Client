package com.jabari.client.network.model;

import com.google.gson.annotations.SerializedName;

public class NearBy {
    @SerializedName("lat")
    String lat;
    @SerializedName("long")
    String long_;
    @SerializedName("dlat")
    String dlat;
    @SerializedName("dlong")
    String dlong;
    @SerializedName("vehicle")
    String vehicle;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong_() {
        return long_;
    }

    public void setLong_(String long_) {
        this.long_ = long_;
    }

    public String getDlat() {
        return dlat;
    }

    public void setDlat(String dlat) {
        this.dlat = dlat;
    }

    public String getDlong() {
        return dlong;
    }

    public void setDlong(String dlong) {
        this.dlong = dlong;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
}
