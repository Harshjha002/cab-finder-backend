package com.example.cabfinder.Response;

import com.example.cabfinder.models.Image;
import com.example.cabfinder.models.Users;

import java.util.List;
import java.util.Optional;

public class OwnerResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String location;
    private String profileImagePath;

    private List<String> citiesProviding;

    public OwnerResponse(Users user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.location = user.getLocation();
        this.profileImagePath = user.getProfileImage() != null
                ? user.getProfileImage().getFilePath()
                : null;
        // ✅ fixed here
        this.citiesProviding = user.getCitiesProviding();
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

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    public List<String> getCitiesProviding() {
        return citiesProviding;
    }

    public void setCitiesProviding(List<String> citiesProviding) {
        this.citiesProviding = citiesProviding;
    }
}
