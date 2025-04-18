package com.example.cabfinder.Response;

import com.example.cabfinder.models.CabType;
import java.util.List;

public class CabResponse {
    private Long id;
    private String model;
    private int seatCapacity;
    private CabType type;
    private double farePerKm;
    private double farePerDay;
    private boolean availability;
    private List<String> images;
    private OwnerInfo owner;

    public CabResponse(Long id, String model, int seatCapacity, CabType type,
                       double farePerKm, double farePerDay, boolean availability,
                       List<String> images, OwnerInfo owner) {
        this.id = id;
        this.model = model;
        this.seatCapacity = seatCapacity;
        this.type = type;
        this.farePerKm = farePerKm;
        this.farePerDay = farePerDay;
        this.availability = availability;
        this.images = images;
        this.owner = owner;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public CabType getType() {
        return type;
    }

    public void setType(CabType type) {
        this.type = type;
    }

    public double getFarePerKm() {
        return farePerKm;
    }

    public void setFarePerKm(double farePerKm) {
        this.farePerKm = farePerKm;
    }

    public double getFarePerDay() {
        return farePerDay;
    }

    public void setFarePerDay(double farePerDay) {
        this.farePerDay = farePerDay;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public OwnerInfo getOwner() {
        return owner;
    }

    public void setOwner(OwnerInfo owner) {
        this.owner = owner;
    }

    // Inner static class for Owner Info
    public static class OwnerInfo {
        private Long id;
        private String username;
        private String email;
        private String phone;
        private String location;
        private String profileImage;

        public OwnerInfo(Long id, String username, String email, String phone,
                         String location, String profileImage) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.phone = phone;
            this.location = location;
            this.profileImage = profileImage;
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

        public String getProfileImage() {
            return profileImage;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }
    }
}
