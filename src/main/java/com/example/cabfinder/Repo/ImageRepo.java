package com.example.cabfinder.Repo;

import com.example.cabfinder.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByCabId(Long cabId);
    Optional<Image> findByOwnerId(Long ownerId);
}

