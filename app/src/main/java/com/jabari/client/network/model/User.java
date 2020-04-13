package com.jabari.client.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class User {
    @SerializedName("email")
    String email;
    @SerializedName("firstName")
    String firstName;
    @SerializedName("mobile")
    String mobileNum;
    @SerializedName("credit")
    String credit;
    @SerializedName("id")
    String id;
    @SerializedName("creditToPay")
    int creditToPay;

    @SerializedName("introduceCode")
    String introduceCode;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("addressList")
    List<String> addressList;
    @SerializedName("avatar")
    String avatar;

    public String getEmail() {
        return email;

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public int getCreditToPay() {
        return creditToPay;
    }

    public void setCreditToPay(int creditToPay) {
        this.creditToPay = creditToPay;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntroduceCode() {
        return introduceCode;
    }

    public void setIntroduceCode(String introduceCode) {
        this.introduceCode = introduceCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

}
