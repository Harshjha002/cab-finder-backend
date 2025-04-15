package com.example.cabfinder.models;

import jakarta.persistence.*;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;

    @ManyToOne
    @JoinColumn(name = "cab_id")
    private Cab cab;

    @OneToOne
    @JoinColumn(name = "owner_id", unique = true)
    private Users owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Cab getCab() {
        return cab;
    }

    public void setCab(Cab cab) {
        this.cab = cab;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}

