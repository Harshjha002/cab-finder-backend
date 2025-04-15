package com.example.cabfinder.controllers;

import com.example.cabfinder.Requests.CreateCabRequest;
import com.example.cabfinder.Requests.UpdateCabRequest;
import com.example.cabfinder.Response.CabResponse;
import com.example.cabfinder.Response.CreateCabResponse;
import com.example.cabfinder.Response.SimpleApiResponse;
import com.example.cabfinder.Response.UpdateCabResponse;
import com.example.cabfinder.models.Cab;
import com.example.cabfinder.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/cab")
@CrossOrigin("http://localhost:5173")
public class CabController {

    @Autowired
    private CabService service;

    // GET: Fetch cab by ID
    @GetMapping("/{id}")
    public ResponseEntity<CabResponse> findCab(@PathVariable Long id) {
        return service.findCabById(id);
    }

    // POST: Add a cab (only if user is an owner)
//    @PostMapping(path = "/{userId}/add-cab", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<CreateCabResponse> addCab(
//            @PathVariable long userId,
//            @RequestPart("cab") CreateCabRequest request,
//            @RequestPart(value = "images", required = false) List<MultipartFile> images
//    ) {
//        return service.addCab(userId, request, images);
//    }

    @PostMapping("/{userId}/add-cab")
    public ResponseEntity<CreateCabResponse> addCab(
            @PathVariable long userId,
            @RequestBody CreateCabRequest request
    ) {
        return service.addCab(userId, request);
    }



    // PUT: Update cab (ownership check included)
    @PutMapping("/{userId}/{cabId}/update-cab")
    public ResponseEntity<UpdateCabResponse> updateCab(
            @PathVariable Long userId,
            @PathVariable Long cabId,
            @RequestBody UpdateCabRequest request
    ) {
        return service.updateCab(userId, cabId, request);
    }

    // DELETE: Delete cab (only by owner)
    @DeleteMapping("/{userId}/{cabId}/delete-cab")
    public ResponseEntity<SimpleApiResponse> deleteCab(
            @PathVariable Long userId,
            @PathVariable Long cabId
    ) {
        return service.deleteCab(userId, cabId);
    }
    @GetMapping("/search")
    public ResponseEntity<List<CabResponse>> findByLocation(@RequestParam String location) {
        location = location.trim(); // just in case
        System.out.println("Searching cabs by location: [" + location + "]");
        return service.getByLocation(location);
    }

    @PutMapping("{userId}/{cabId}/changeAbility")
    public ResponseEntity<?> changeCabAvailability(@PathVariable Long userId, @PathVariable Long cabId) {
        return service.toggleCabAvailability(userId, cabId);
    }


}
