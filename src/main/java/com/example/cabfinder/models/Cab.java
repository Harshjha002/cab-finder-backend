package com.example.cabfinder.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private boolean availability = false;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonBackReference
    private Users owner;

    @OneToMany(mappedBy = "cab", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Cab() {}

    public Cab(Long id, String model, int seatCapacity, CabType type, double farePerKm,
               double farePerDay, boolean availability, Users owner) {
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
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getModel() { return model; }

    public void setModel(String model) { this.model = model; }

    public int getSeatCapacity() { return seatCapacity; }

    public void setSeatCapacity(int seatCapacity) { this.seatCapacity = seatCapacity; }

    public CabType getType() { return type; }

    public void setType(CabType type) { this.type = type; }

    public double getFarePerKm() { return farePerKm; }

    public void setFarePerKm(double farePerKm) { this.farePerKm = farePerKm; }

    public double getFarePerDay() { return farePerDay; }

    public void setFarePerDay(double farePerDay) { this.farePerDay = farePerDay; }

    public boolean getAvailability() { return availability; }

    public void setAvailability(boolean availability) { this.availability = availability; }

    public Users getOwner() { return owner; }

    public void setOwner(Users owner) { this.owner = owner; }

    public List<Image> getImages() { return images; }

    public void setImages(List<Image> images) { this.images = images; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Cab{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", seatCapacity=" + seatCapacity +
                ", type=" + type +
                ", farePerKm=" + farePerKm +
                ", farePerDay=" + farePerDay +
                ", availability=" + availability +
                ", ownerId=" + (owner != null ? owner.getId() : null) +
                '}';
    }
}
