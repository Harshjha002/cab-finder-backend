package com.example.cabfinder.Requests;

public class UpdateUserRequest {

    private Long userId; // to identify which user

    private String username;
    private String email;
    private String phone;
    private String location;
    private String password;

    public UpdateUserRequest() {}

    public UpdateUserRequest(Long userId, String username, String email, String phone, String location, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.location = location;
        this.password = password;
    }

    // Getters and setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
