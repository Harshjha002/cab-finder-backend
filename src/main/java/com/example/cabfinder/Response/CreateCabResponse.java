package com.example.cabfinder.Response;

public class CreateCabResponse {
    private String message;
    private Long cabId;

    public CreateCabResponse() {
    }

    public CreateCabResponse(String message, Long cabId) {
        this.message = message;
        this.cabId = cabId;
    }

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
}
