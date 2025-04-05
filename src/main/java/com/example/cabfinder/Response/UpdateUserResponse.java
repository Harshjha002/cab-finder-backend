package com.example.cabfinder.Response;

import java.util.List;

public class UpdateUserResponse {

    private String message;
    private Long userId;
    private List<String> updatedFields;

    public UpdateUserResponse() {}

    public UpdateUserResponse(String message, Long userId, List<String> updatedFields) {
        this.message = message;
        this.userId = userId;
        this.updatedFields = updatedFields;
    }

    // Getters and setters

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

    public List<String> getUpdatedFields() {
        return updatedFields;
    }

    public void setUpdatedFields(List<String> updatedFields) {
        this.updatedFields = updatedFields;
    }
}
