package com.jabari.client.network.model;

public class Finance {

    private String id;
    private String calculated;
    private String vehicle;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCalculated() {
        return calculated;
    }

    public void setCalculated(String calculated) {
        this.calculated = calculated;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    private String createdAt;

}
