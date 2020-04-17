package com.jabari.client.network.model;

import com.google.gson.annotations.SerializedName;

public class Travel {

    @SerializedName("date")
    String date;
    String sender_name;
    @SerializedName("locationAddress")
    String sender_location;
    String sender_phone;
    String reciever_name;
    @SerializedName("destinationAddress")
    String reciever_location;
    String recirer_phone;
    @SerializedName("id")
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_location() {
        return sender_location;
    }

    public void setSender_location(String sender_location) {
        this.sender_location = sender_location;
    }

    public String getSender_phone() {
        return sender_phone;
    }

    public void setSender_phone(String sender_phone) {
        this.sender_phone = sender_phone;
    }

    public String getReciever_name() {
        return reciever_name;
    }

    public void setReciever_name(String reciever_name) {
        this.reciever_name = reciever_name;
    }

    public String getReciever_location() {
        return reciever_location;
    }

    public void setReciever_location(String reciever_location) {
        this.reciever_location = reciever_location;
    }

    public String getRecirer_phone() {
        return recirer_phone;
    }

    public void setRecirer_phone(String recirer_phone) {
        this.recirer_phone = recirer_phone;
    }
}
