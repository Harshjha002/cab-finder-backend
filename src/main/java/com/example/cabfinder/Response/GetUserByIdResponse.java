package com.example.cabfinder.Response;

import com.example.cabfinder.models.Cab;
import java.util.List;

public class GetUserByIdResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String location;
    private boolean isOwner;

    private List<Cab> cabs; // Will be empty or null based on isOwner

    public GetUserByIdResponse() {}

    public GetUserByIdResponse(Long id, String username, String email, String phone, String location, boolean isOwner, List<Cab> cabs) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.isOwner = isOwner;
        this.cabs = cabs;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public List<Cab> getCabs() {
        return cabs;
    }

    public void setCabs(List<Cab> cabs) {
        this.cabs = cabs;
    }
}
