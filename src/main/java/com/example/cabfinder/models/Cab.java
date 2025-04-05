package com.example.cabfinder.models;

import jakarta.persistence.*;

@Entity
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private int seatCapacity;

    @Enumerated(EnumType.STRING)
    private CabType type;

    private double farePerKm;
    private double farePerDay;
    private boolean availability;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;

    // No-args constructor
    public Cab() {}

    // All-args constructor
    public Cab(Long id, String model, int seatCapacity, CabType type, double farePerKm, double farePerDay, boolean availability, Users owner) {
        this.id = id;
        this.model = model;
        this.seatCapacity = seatCapacity;
        this.type = type;
        this.farePerKm = farePerKm;
        this.farePerDay = farePerDay;
        this.availability = availability;
        this.owner = owner;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}
