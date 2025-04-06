package com.example.cabfinder.service;

import com.example.cabfinder.Repo.CabRepo;
import com.example.cabfinder.Repo.UserRepo;
import com.example.cabfinder.Requests.CreateCabRequest;
import com.example.cabfinder.Requests.UpdateCabRequest;
import com.example.cabfinder.Response.CabResponse;
import com.example.cabfinder.Response.CreateCabResponse;
import com.example.cabfinder.Response.SimpleApiResponse;
import com.example.cabfinder.Response.UpdateCabResponse;
import com.example.cabfinder.models.Cab;
import com.example.cabfinder.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CabService {

    @Autowired
    private CabRepo repo;

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<CreateCabResponse> addCab(long id, CreateCabRequest request) {
        Optional<Users> optionalUser = userRepo.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new CreateCabResponse("User not found", null));
        }

        Users user = optionalUser.get();

        if (!user.isOwner()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new CreateCabResponse("User is not an owner", null));
        }

        Cab cab = new Cab();
        cab.setModel(request.getModel());
        cab.setSeatCapacity(request.getSeatCapacity());
        cab.setType(request.getType());
        cab.setFarePerKm(request.getFarePerKm());
        cab.setFarePerDay(request.getFarePerDay());
        cab.setAvailability(false); // default value
        cab.setOwner(user);

        Cab savedCab = repo.save(cab); // make sure this is cabRepo

        return ResponseEntity.ok(
                new CreateCabResponse("Cab created successfully", savedCab.getId())
        );
    }


    public ResponseEntity<CabResponse> findCabById(Long id) {
        Optional<Cab> optionalCab = repo.findById(id);

        if (optionalCab.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Cab cab = optionalCab.get();
        Users owner = cab.getOwner();

        CabResponse.OwnerInfo ownerInfo = new CabResponse.OwnerInfo(
                owner.getId(),
                owner.getUsername(),
                owner.getEmail(),
                owner.getPhone(),
                owner.getLocation()
        );

        CabResponse response = new CabResponse(
                cab.getId(),
                cab.getModel(),
                cab.getSeatCapacity(),
                cab.getType(),
                cab.getFarePerKm(),
                cab.getFarePerDay(),
                cab.getAvailability(),
                ownerInfo
        );

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<SimpleApiResponse> deleteCab(Long userId, Long cabId) {
        Optional<Cab> optionalCab = repo.findById(cabId);

        if (optionalCab.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new SimpleApiResponse("Cab not found", false));
        }

        Cab cab = optionalCab.get();

        if (!cab.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new SimpleApiResponse("Unauthorized. Only the owner can delete this cab", false));
        }

        repo.deleteById(cabId);
        return ResponseEntity.ok(new SimpleApiResponse("Cab deleted successfully", true));
    }

    public ResponseEntity<UpdateCabResponse> updateCab(Long userId, Long cabId, UpdateCabRequest request) {
        Optional<Cab> optionalCab = repo.findById(cabId);

        if (optionalCab.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new UpdateCabResponse("Cab not found", null, List.of()));
        }

        Cab cab = optionalCab.get();

        if (!cab.getOwner().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new UpdateCabResponse("You are not the owner of this cab", cabId, List.of()));
        }

        // Now update only changed fields
        List<String> updatedFields = new ArrayList<>();

        if (request.getModel() != null && !request.getModel().equals(cab.getModel())) {
            cab.setModel(request.getModel());
            updatedFields.add("model");
        }
        if (request.getSeatCapacity() != null && request.getSeatCapacity() != cab.getSeatCapacity()) {
            cab.setSeatCapacity(request.getSeatCapacity());
            updatedFields.add("seatCapacity");
        }
        if (request.getType() != null && !request.getType().equals(cab.getType())) {
            cab.setType(request.getType());
            updatedFields.add("type");
        }
        if (request.getFarePerKm() != null && request.getFarePerKm() != cab.getFarePerKm()) {
            cab.setFarePerKm(request.getFarePerKm());
            updatedFields.add("farePerKm");
        }
        if (request.getFarePerDay() != null && request.getFarePerDay() != cab.getFarePerDay()) {
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


    public ResponseEntity<List<CabResponse>> getByLocation(String location) {
        List<Cab> cabs = repo.findByOwner_LocationIgnoreCase(location);

//        List<Cab> cabs = repo.findByOwner_LocationIgnoreCase("New York");

        List<Cab> allCabs = repo.findAll();
        for (Cab c : allCabs) {
            System.out.println("Cab: " + c.getModel() + " | Owner location: [" + c.getOwner().getLocation() + "]");
        }


        List<CabResponse> responses = cabs.stream()
                .map(cab -> new CabResponse(
                        cab.getId(),
                        cab.getModel(),
                        cab.getSeatCapacity(),
                        cab.getType(),
                        cab.getFarePerKm(),
                        cab.getFarePerDay(),
                        cab.getAvailability(),
                        new CabResponse.OwnerInfo(
                                cab.getOwner().getId(),
                                cab.getOwner().getUsername(),
                                cab.getOwner().getEmail(),
                                cab.getOwner().getPhone(),
                                cab.getOwner().getLocation()
                        )
                ))
                .toList();

        return ResponseEntity.ok(responses);
    }

}
