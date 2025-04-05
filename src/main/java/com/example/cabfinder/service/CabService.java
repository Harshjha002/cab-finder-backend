package com.example.cabfinder.service;

import com.example.cabfinder.Repo.CabRepo;
import com.example.cabfinder.Repo.UserRepo;
import com.example.cabfinder.Requests.CreateCabRequest;
import com.example.cabfinder.Response.CabResponse;
import com.example.cabfinder.Response.CreateCabResponse;
import com.example.cabfinder.models.Cab;
import com.example.cabfinder.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

}
