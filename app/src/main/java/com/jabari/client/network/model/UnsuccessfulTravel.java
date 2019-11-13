package com.jabari.client.network.model;

public class UnsuccessfulTravel extends Travel {

    String payment_way;
    String transfer_payment;
    String payment_company;
    String take_back;
    String detail;
    String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPayment_way() {
        return payment_way;
    }

    public void setPayment_way(String payment_way) {
        this.payment_way = payment_way;
    }

    public String getTransfer_payment() {
        return transfer_payment;
    }

    public void setTransfer_payment(String transfer_payment) {
        this.transfer_payment = transfer_payment;
    }

    public String getPayment_company() {
        return payment_company;
    }

    public void setPayment_company(String payment_company) {
        this.payment_company = payment_company;
    }

    public String getTake_back() {
        return take_back;
    }

    public void setTake_back(String take_back) {
        this.take_back = take_back;
    }
}
