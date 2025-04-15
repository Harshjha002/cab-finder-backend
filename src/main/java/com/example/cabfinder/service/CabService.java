package com.example.cabfinder.service;

import com.example.cabfinder.Repo.CabRepo;
import com.example.cabfinder.Repo.ImageRepo;
import com.example.cabfinder.Repo.UserRepo;
import com.example.cabfinder.Requests.CreateCabRequest;
import com.example.cabfinder.Requests.UpdateCabRequest;
import com.example.cabfinder.Response.CabResponse;
import com.example.cabfinder.Response.CreateCabResponse;
import com.example.cabfinder.Response.SimpleApiResponse;
import com.example.cabfinder.Response.UpdateCabResponse;
import com.example.cabfinder.models.Cab;
import com.example.cabfinder.models.Image;
import com.example.cabfinder.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class that handles all business logic related to Cab operations.
 * Includes creating, updating, deleting, and retrieving cab information.
 */
@Service
public class CabService {

    @Autowired
    private CabRepo repo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ImageRepo imgRepo;

    /**
     * Creates a new cab for a given owner.
     * Validates if user exists and is an owner before creating the cab.
     *
     * @param id      User ID of the cab owner
     * @param request Cab creation request payload
     * @return ResponseEntity with cab creation status and ID
     */
    public ResponseEntity<CreateCabResponse> addCab(long id, CreateCabRequest request) {
        Optional<Users> optionalUser = userRepo.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body(new CreateCabResponse("User not found", null));
        }

        Users user = optionalUser.get();

        if (!user.isOwner()) {
            return ResponseEntity.status(400).body(new CreateCabResponse("User is not an owner", null));
        }

        Cab cab = new Cab();
        cab.setModel(request.getModel());
        cab.setSeatCapacity(request.getSeatCapacity());
        cab.setType(request.getType());
        cab.setFarePerKm(request.getFarePerKm());
        cab.setFarePerDay(request.getFarePerDay());
        cab.setAvailability(false);
        cab.setOwner(user);

        Cab savedCab = repo.save(cab);

        return ResponseEntity.ok(new CreateCabResponse("Cab created successfully", savedCab.getId()));
    }

    /**
     * Retrieves a cab by its ID with full owner and image details.
     *
     * @param id Cab ID
     * @return ResponseEntity with CabResponse
     */
    public ResponseEntity<CabResponse> findCabById(Long id) {
        Optional<Cab> optionalCab = repo.findById(id);

        if (optionalCab.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cab cab = optionalCab.get();
        Users owner = cab.getOwner();

        // Convert image file paths to list
        List<String> imageUrls = cab.getImages().stream()
                .map(Image::getFilePath)
                .toList();

        // Build owner info
        CabResponse.OwnerInfo ownerInfo = new CabResponse.OwnerInfo(
                owner.getId(),
                owner.getUsername(),
                owner.getEmail(),
                owner.getPhone(),
                owner.getLocation(),
                owner.getProfileImage() != null ? owner.getProfileImage().getFilePath() : null
        );

        CabResponse response = new CabResponse(
                cab.getId(),
                cab.getModel(),
                cab.getSeatCapacity(),
                cab.getType(),
                cab.getFarePerKm(),
                cab.getFarePerDay(),
                cab.getAvailability(),
                imageUrls,
                ownerInfo
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a cab if the requesting user is the owner.
     *
     * @param userId ID of the user attempting deletion
     * @param cabId  ID of the cab to delete
     * @return ResponseEntity with status
     */
    public ResponseEntity<SimpleApiResponse> deleteCab(Long userId, Long cabId) {
        Optional<Cab> optionalCab = repo.findById(cabId);

        if (optionalCab.isEmpty()) {
            return ResponseEntity.status(404).body(new SimpleApiResponse("Cab not found", false));
        }

        Cab cab = optionalCab.get();

        if (!cab.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(403).body(new SimpleApiResponse("Unauthorized. Only the owner can delete this cab", false));
        }

        repo.deleteById(cabId);
        return ResponseEntity.ok(new SimpleApiResponse("Cab deleted successfully", true));
    }

    /**
     * Updates only the changed fields of a cab if the user is the owner.
     *
     * @param userId  ID of the user attempting the update
     * @param cabId   ID of the cab to update
     * @param request Update payload
     * @return ResponseEntity with list of updated fields
     */
    public ResponseEntity<UpdateCabResponse> updateCab(Long userId, Long cabId, UpdateCabRequest request) {
        Optional<Cab> optionalCab = repo.findById(cabId);

        if (optionalCab.isEmpty()) {
            return ResponseEntity.status(404).body(new UpdateCabResponse("Cab not found", null, List.of()));
        }

        Cab cab = optionalCab.get();

        if (!cab.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(403).body(new UpdateCabResponse("You are not the owner of this cab", cabId, List.of()));
        }

        List<String> updatedFields = new ArrayList<>();

        if (request.getModel() != null && !request.getModel().equals(cab.getModel())) {
            cab.setModel(request.getModel());
            updatedFields.add("model");
        }
        if (request.getSeatCapacity() != null && !request.getSeatCapacity().equals(cab.getSeatCapacity())) {
            cab.setSeatCapacity(request.getSeatCapacity());
            updatedFields.add("seatCapacity");
        }
        if (request.getType() != null && !request.getType().equals(cab.getType())) {
            cab.setType(request.getType());
            updatedFields.add("type");
        }
        if (request.getFarePerKm() != null && !request.getFarePerKm().equals(cab.getFarePerKm())) {
            cab.setFarePerKm(request.getFarePerKm());
            updatedFields.add("farePerKm");
        }
        if (request.getFarePerDay() != null && !request.getFarePerDay().equals(cab.getFarePerDay())) {
            cab.setFarePerDay(request.getFarePerDay());
            updatedFields.add("farePerDay");
        }

        if (!updatedFields.isEmpty()) {
            repo.save(cab);
        }

        return ResponseEntity.ok(
                new UpdateCabResponse(
                        updatedFields.isEmpty() ? "No fields updated" : "Cab updated successfully",
                        cab.getId(),
                        updatedFields
                )
        );
    }

    /**
     * Fetches all cabs where the owner's location matches the input location.
     *
     * @param location source city name
     * @return List of matching CabResponse
     */
    public ResponseEntity<List<CabResponse>> getByLocation(String location) {
        List<Cab> cabs = repo.findByOwner_LocationIgnoreCase(location);

        List<CabResponse> responses = cabs.stream().map(cab -> {
            List<String> imagePaths = cab.getImages().stream().map(Image::getFilePath).toList();

            return new CabResponse(
                    cab.getId(),
                    cab.getModel(),
                    cab.getSeatCapacity(),
                    cab.getType(),
                    cab.getFarePerKm(),
                    cab.getFarePerDay(),
                    cab.getAvailability(),
                    imagePaths,
                    new CabResponse.OwnerInfo(
                            cab.getOwner().getId(),
                            cab.getOwner().getUsername(),
                            cab.getOwner().getEmail(),
                            cab.getOwner().getPhone(),
                            cab.getOwner().getLocation(),
                            cab.getOwner().getProfileImage() != null
                                    ? cab.getOwner().getProfileImage().getFilePath()
                                    : null
                    )
            );
        }).toList();

        return ResponseEntity.ok(responses);
    }

    /**
     * Toggles the availability status of a cab by its owner.
     *
     * @param userId ID of the user attempting the action
     * @param cabId  ID of the cab
     * @return ResponseEntity with new availability status
     */
    public ResponseEntity<?> toggleCabAvailability(Long userId, Long cabId) {
        Cab cab = repo.findById(cabId)
                .orElseThrow(() -> new RuntimeException("Cab not found"));

        if (!cab.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(403).body("You are not the owner of this cab.");
        }

        cab.setAvailability(!cab.getAvailability());
        repo.save(cab);

        return ResponseEntity.ok("Cab availability changed to: " + cab.getAvailability());
    }
}
