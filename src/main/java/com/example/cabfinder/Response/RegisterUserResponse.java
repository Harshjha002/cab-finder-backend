package com.example.cabfinder.Response;

public class RegisterUserResponse {

    private String message;
    private Long userId;

    public RegisterUserResponse() {}

    public RegisterUserResponse(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

    // Getters and Setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
