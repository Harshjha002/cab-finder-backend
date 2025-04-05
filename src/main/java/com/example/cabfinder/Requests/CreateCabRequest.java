package com.example.cabfinder.Requests;

import com.example.cabfinder.models.CabType;

public class CreateCabRequest {
    private String model;
    private int seatCapacity;
    private CabType type;
    private double farePerKm;
    private double farePerDay;
    private Long ownerId;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public CabType getType() {
        return type;
    }

    public void setType(CabType type) {
        this.type = type;
    }

    public double getFarePerKm() {
        return farePerKm;
    }

    public void setFarePerKm(double farePerKm) {
        this.farePerKm = farePerKm;
    }

    public double getFarePerDay() {
        return farePerDay;
    }

    public void setFarePerDay(double farePerDay) {
        this.farePerDay = farePerDay;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}