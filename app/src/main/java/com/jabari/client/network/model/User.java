package com.jabari.client.network.model;

import com.google.gson.annotations.SerializedName;


public class User {
    @SerializedName("email")
    String Email;
    @SerializedName("firstName")
    String FirstName;
    @SerializedName("mobile")
    String mobileNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    String id;
    @SerializedName("jwtAccessToken")

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }


    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

   }
