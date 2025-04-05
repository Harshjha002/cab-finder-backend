package com.example.cabfinder.Response;

import com.example.cabfinder.models.Cab;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String location;
    private boolean isOwner;

    // Only included if user is owner
    private List<Cab> cabs;

    public SignInResponse() {}

    // Constructor without cabs (non-owner)
    public SignInResponse(Long id, String username, String email, String phone, String location, boolean isOwner) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.isOwner = isOwner;
    }

    // Constructor with cabs (owner)
    public SignInResponse(Long id, String username, String email, String phone, String location, boolean isOwner, List<Cab> cabs) {
        this(id, username, email, phone, location, isOwner);
        this.cabs = cabs;
    }

    // Getters and Setters

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
