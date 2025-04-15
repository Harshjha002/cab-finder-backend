package com.example.cabfinder.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String phone;
    private String location;

    private boolean isOwner = false;
    private String role = "USER"; // Default role
    private String password;

    // Store profile image path (not as an entity)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_image_id")
    private Image profileImage;


    @ElementCollection
    private List<String> citiesProviding = new ArrayList<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Cab> cabs = new ArrayList<>();

    // --- Constructors ---

    public Users() {
    }

    public Users(Long id, String username, String email, String phone, String location, boolean isOwner, String role, String password, Image profileImage, List<String> citiesProviding, List<Cab> cabs) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.isOwner = isOwner;
        this.role = role;
        this.password = password;
        this.profileImage = profileImage;
        this.citiesProviding = citiesProviding;
        this.cabs = cabs;
    }

    // --- Getters & Setters ---


    public List<Cab> getCabs() {
        return cabs;
    }

    public void setCabs(List<Cab> cabs) {
        this.cabs = cabs;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Image getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Image profileImage) {
        this.profileImage = profileImage;
    }

    public List<String> getCitiesProviding() {
        return citiesProviding;
    }

    public void setCitiesProviding(List<String> citiesProviding) {
        this.citiesProviding = citiesProviding;
    }
}