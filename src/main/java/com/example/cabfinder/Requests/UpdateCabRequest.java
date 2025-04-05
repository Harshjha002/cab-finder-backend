package com.example.cabfinder.Requests;

import com.example.cabfinder.models.CabType;

public class UpdateCabRequest {

    private String model;
    private Integer seatCapacity;
    private CabType type;
    private Double farePerKm;
    private Double farePerDay;
    private Boolean availability;

    // Getters and setters
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public CabType getType() {
        return type;
    }

    public void setType(CabType type) {
        this.type = type;
    }

    public Double getFarePerKm() {
        return farePerKm;
    }

    public void setFarePerKm(Double farePerKm) {
        this.farePerKm = farePerKm;
    }

    public Double getFarePerDay() {
        return farePerDay;
    }

    public void setFarePerDay(Double farePerDay) {
        this.farePerDay = farePerDay;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }
}
