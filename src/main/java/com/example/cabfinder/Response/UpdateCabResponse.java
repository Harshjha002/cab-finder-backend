package com.example.cabfinder.Response;

import java.util.List;

public class UpdateCabResponse {
    private String message;
    private Long cabId;
    private List<String> updatedFields;

    public UpdateCabResponse(String message, Long cabId, List<String> updatedFields) {
        this.message = message;
        this.cabId = cabId;
        this.updatedFields = updatedFields;
    }

    // Getters and setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCabId() {
        return cabId;
    }

    public void setCabId(Long cabId) {
        this.cabId = cabId;
    }

    public List<String> getUpdatedFields() {
        return updatedFields;
    }

    public void setUpdatedFields(List<String> updatedFields) {
        this.updatedFields = updatedFields;
    }
}
