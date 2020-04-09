package com.jabari.client.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;

    @SerializedName("dlat")
    @Expose
    private String dlat;
    @SerializedName("dlng")
    @Expose
    private String dlng;
    @SerializedName("vehicle")
    @Expose
    private int vehicle;
    @SerializedName("stop")
    @Expose
    private int stop;
    @SerializedName("haveReturn")
    @Expose
    private int haveReturn;
    @SerializedName("cashPayment")
    @Expose
    private int cashPayment;
    @SerializedName("payByRequest")
    @Expose
    private int payByRequest;
    @SerializedName("destinationAddress")
    @Expose
    private String destinationAddress;
    @SerializedName("locationAddress")
    @Expose
    private String locationAddress;
    @SerializedName("message")
    private String message;


    public String getMessage() {
        return message;
    }


    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getDlat() {
        return dlat;
    }

    public void setDlat(String dlat) {
        this.dlat = dlat;
    }

    public String getDlng() {
        return dlng;
    }

    public void setDlng(String dlng) {
        this.dlng = dlng;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public int getStop() {
        return stop;
    }

    public void setStop(int stop) {
        this.stop = stop;
    }

    public int getHaveReturn() {
        return haveReturn;
    }

    public void setHaveReturn(int haveReturn) {
        this.haveReturn = haveReturn;
    }

    public int getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(int cashPayment) {
        this.cashPayment = cashPayment;
    }

    public int getPayByRequest() {
        return payByRequest;
    }

    public void setPayByRequest(int payByRequest) {
        this.payByRequest = payByRequest;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }


}
