package com.example.cabfinder.controllers;

import com.example.cabfinder.Requests.CreateCabRequest;
import com.example.cabfinder.Requests.UpdateCabRequest;
import com.example.cabfinder.Response.CabResponse;
import com.example.cabfinder.Response.CreateCabResponse;
import com.example.cabfinder.Response.SimpleApiResponse;
import com.example.cabfinder.Response.UpdateCabResponse;
import com.example.cabfinder.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller to handle all cab-related operations such as creation, updating, deletion,
 * retrieval, and searching. Supports cross-origin requests for frontend integration.
 */
@RestController
@RequestMapping("/api/cab")
@CrossOrigin("http://localhost:5173")
public class CabController {

    @Autowired
    private CabService service;

    /**
     * Fetches a cab by its ID.
     *
     * @param id The ID of the cab to fetch.
     * @return Cab details wrapped in a CabResponse.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CabResponse> findCab(@PathVariable Long id) {
        return service.findCabById(id);
    }

    /**
     * Adds a new cab for a given owner (user must be an owner).
     *
     * @param userId  The ID of the user (owner).
     * @param request Cab creation details.
     * @return Response indicating success or failure of cab creation.
     */
    @PostMapping("/{userId}/add-cab")
    public ResponseEntity<CreateCabResponse> addCab(
            @PathVariable long userId,
            @RequestBody CreateCabRequest request
    ) {
        return service.addCab(userId, request);
    }

    // Uncomment below to support image uploads with cab creation.
    /*
    @PostMapping(path = "/{userId}/add-cab", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreateCabResponse> addCab(
            @PathVariable long userId,
            @RequestPart("cab") CreateCabRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        return service.addCab(userId, request, images);
    }
    */

    /**
     * Updates an existing cab’s details. Only the owner of the cab is authorized.
     *
     * @param userId  The ID of the owner.
     * @param cabId   The ID of the cab to update.
     * @param request Partial or full updated cab data.
     * @return Updated cab information.
     */
    @PutMapping("/{userId}/{cabId}/update-cab")
    public ResponseEntity<UpdateCabResponse> updateCab(
            @PathVariable Long userId,
            @PathVariable Long cabId,
            @RequestBody UpdateCabRequest request
    ) {
        return service.updateCab(userId, cabId, request);
    }

    /**
     * Deletes a cab by its ID. Only accessible to the owner of the cab.
     *
     * @param userId The ID of the cab owner.
     * @param cabId  The ID of the cab to delete.
     * @return SimpleApiResponse indicating result.
     */
    @DeleteMapping("/{userId}/{cabId}/delete-cab")
    public ResponseEntity<SimpleApiResponse> deleteCab(
            @PathVariable Long userId,
            @PathVariable Long cabId
    ) {
        return service.deleteCab(userId, cabId);
    }

    /**
     * Searches for available cabs based on the owner's location.
     *
     * @param location City name used to match owner's base location.
     * @return List of matching CabResponse DTOs.
     */
    @GetMapping("/search")
    public ResponseEntity<List<CabResponse>> findByLocation(@RequestParam String location) {
        location = location.trim(); // Trim input to avoid issues due to whitespace
        System.out.println("Searching cabs by location: [" + location + "]");
        return service.getByLocation(location);
    }

    /**
     * Toggles the availability status of a cab (e.g., available <-> unavailable).
     * Useful for letting owners temporarily deactivate their cab listings.
     *
     * @param userId The ID of the owner.
     * @param cabId  The ID of the cab to update availability for.
     * @return Response indicating the new status.
     */
    @PutMapping("{userId}/{cabId}/changeAbility")
    public ResponseEntity<?> changeCabAvailability(@PathVariable Long userId, @PathVariable Long cabId) {
        return service.toggleCabAvailability(userId, cabId);
    }
}
