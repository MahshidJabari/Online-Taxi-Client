package com.jabari.client.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Travel implements Serializable {
    @SerializedName("location")
    private ArrayList<String> location;

    @SerializedName("Destination")
    private ArrayList<String> Destination;
    @SerializedName("vehicle")
    @Expose
    private String vehicle;
    @SerializedName("stop")
    @Expose
    private boolean stop;
    @SerializedName("haveReturn")
    @Expose
    private boolean haveReturn;
    @SerializedName("cashPayment")
    @Expose
    private int cashPayment;
    @SerializedName("payByRequest")
    @Expose
    private boolean payByRequest;
    @SerializedName("destinationAddress")
    @Expose
    private String destinationAddress;
    @SerializedName("locationAddress")
    @Expose
    private String locationAddress;
    @SerializedName("id")
    private String id;
    @SerializedName("createdAt")
    private String createdAt;

    public ArrayList<String> getLocation() {
        return location;
    }

    public void setLocation(ArrayList<String> location) {
        this.location = location;
    }

    public ArrayList<String> getDestination() {
        return Destination;
    }

    public void setDestination(ArrayList<String> destination) {
        Destination = destination;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }


    public int getCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(int cashPayment) {
        this.cashPayment = cashPayment;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public boolean isHaveReturn() {
        return haveReturn;
    }

    public void setHaveReturn(boolean haveReturn) {
        this.haveReturn = haveReturn;
    }

    public boolean isPayByRequest() {
        return payByRequest;
    }

    public void setPayByRequest(boolean payByRequest) {
        this.payByRequest = payByRequest;
    }
}
