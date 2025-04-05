package com.example.cabfinder.service;

import com.example.cabfinder.Repo.UserRepo;
import com.example.cabfinder.Requests.RegisterUserRequest;
import com.example.cabfinder.Requests.SignInRequest;
import com.example.cabfinder.Requests.UpdateUserRequest;
import com.example.cabfinder.Response.*;
import com.example.cabfinder.models.Cab;
import com.example.cabfinder.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo repo;


    public ResponseEntity<RegisterUserResponse> register(RegisterUserRequest request) {
        Optional<Users> existingUser = repo.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new RegisterUserResponse("Email already exists", null));
        }

        Users newUser = new Users();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());
        newUser.setLocation(request.getLocation());
        newUser.setPassword(request.getPassword());
        newUser.setOwner(false);

        Users savedUser = repo.save(newUser);

        return ResponseEntity.ok(
                new RegisterUserResponse("User registered successfully", savedUser.getId())
        );
    }

    public ResponseEntity<SignInResponse> signin(SignInRequest request) {
        Optional<Users> optionalUser = repo.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(null); // or a custom error response
        }

        Users user = optionalUser.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(null); // or return a message like "Invalid password"
        }

        if (user.isOwner()) {
            List<Cab> cabs = user.getCabs(); // assuming mapped properly
            return ResponseEntity.ok(
                    new SignInResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getPhone(),
                            user.getLocation(),
                            true,
                            cabs
                    )
            );
        } else {
            return ResponseEntity.ok(
                    new SignInResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getPhone(),
                            user.getLocation(),
                            false
                    )
            );
        }
    }

    public ResponseEntity<GetUserByIdResponse> findUserById(Long id) {
        Optional<Users> userOpt = repo.findById(id);

        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Users user = userOpt.get();

        List<Cab> cabs = user.isOwner() ? user.getCabs() : null;
        if (cabs == null) {
            cabs = List.of(); // Return empty list if owner but has no cabs
        }

        GetUserByIdResponse response = new GetUserByIdResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getLocation(),
                user.isOwner(),
                user.isOwner() ? cabs : null
        );

        return ResponseEntity.ok(response);
    }



    public ResponseEntity<UpdateUserResponse> updateUser(Long id, UpdateUserRequest request) {
        Optional<Users> optionalUser = repo.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Users user = optionalUser.get();
        List<String> updatedFields = new ArrayList<>();

        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            user.setUsername(request.getUsername());
            updatedFields.add("username");
        }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            user.setEmail(request.getEmail());
            updatedFields.add("email");
        }

        if (request.getPhone() != null && !request.getPhone().equals(user.getPhone())) {
            user.setPhone(request.getPhone());
            updatedFields.add("phone");
        }

        if (request.getLocation() != null && !request.getLocation().equals(user.getLocation())) {
            user.setLocation(request.getLocation());
            updatedFields.add("location");
        }

        if (request.getPassword() != null && !request.getPassword().equals(user.getPassword())) {
            user.setPassword(request.getPassword());
            updatedFields.add("password");
        }

        if (!updatedFields.isEmpty()) {
            repo.save(user);
        }

        UpdateUserResponse response = new UpdateUserResponse(
                updatedFields.isEmpty() ? "No fields updated" : "User updated successfully",
                user.getId(),
                updatedFields
        );

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<SimpleApiResponse> updateToOwner(long id) {
        Optional<Users> optionalUser = repo.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new SimpleApiResponse("User not found", false));
        }

        Users user = optionalUser.get();

        if (user.isOwner()) {
            return ResponseEntity.ok(new SimpleApiResponse("User is already an owner", false));
        }

        user.setOwner(true);
        repo.save(user);

        return ResponseEntity.ok(new SimpleApiResponse("User upgraded to owner successfully", true));
    }

}
