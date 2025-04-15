package com.example.cabfinder.service;

import com.example.cabfinder.models.Image;
import com.fasterxml.jackson.core.type.TypeReference;

import com.example.cabfinder.Repo.UserRepo;
import com.example.cabfinder.Requests.RegisterUserRequest;
import com.example.cabfinder.Requests.SignInRequest;
import com.example.cabfinder.Requests.UpdateUserRequest;
import com.example.cabfinder.Response.*;
import com.example.cabfinder.models.Cab;
import com.example.cabfinder.models.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
                    .body(null);
        }

        Users user = optionalUser.get();

        if (!user.getPassword().equals(request.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(null);
        }

        if (user.isOwner()) {
            List<Cab> cabs = user.getCabs();
            String profileImagePath = user.getProfileImage() != null ? user.getProfileImage().getFilePath() : null;
            List<String> cities = user.getCitiesProviding();

            return ResponseEntity.ok(
                    new SignInResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getPhone(),
                            user.getLocation(),
                            true,
                            cabs,
                            profileImagePath,
                            cities
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

        if (user.isOwner()) {
            List<Cab> cabs = user.getCabs() != null ? user.getCabs() : List.of();
            String profileImagePath = user.getProfileImage() != null ? user.getProfileImage().getFilePath() : null;
            List<String> cities = user.getCitiesProviding() != null ? user.getCitiesProviding() : List.of();

            GetUserByIdResponse response = new GetUserByIdResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getLocation(),
                    true,
                    cabs,
                    profileImagePath,
                    cities
            );

            return ResponseEntity.ok(response);
        } else {
            GetUserByIdResponse response = new GetUserByIdResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPhone(),
                    user.getLocation(),
                    false
            );

            return ResponseEntity.ok(response);
        }
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

//    public ResponseEntity<SimpleApiResponse> updateToOwner(long id) {
//        Optional<Users> optionalUser = repo.findById(id);
//
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new SimpleApiResponse("User not found", false));
//        }
//
//        Users user = optionalUser.get();
//
//        if (user.isOwner()) {
//            return ResponseEntity.ok(new SimpleApiResponse("User is already an owner", false));
//        }
//
//        user.setOwner(true);
//        repo.save(user);
//
//        return ResponseEntity.ok(new SimpleApiResponse("User upgraded to owner successfully", true));
//    }

    public ResponseEntity<?> upgradeToOwner(Long userId, MultipartFile file, String citiesJson) {
        try {
            Users user = repo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user.isOwner()) {
                return ResponseEntity.badRequest().body("User is already an owner");
            }

            List<String> cities = new ObjectMapper().readValue(
                    citiesJson, new TypeReference<List<String>>() {}
            );

            String uploadDir = "uploads/owners/" + userId + "/";
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Image profileImage = new Image();
            profileImage.setFilePath(filePath.toString());
            profileImage.setOwner(user);

            // Update user
            user.setOwner(true);
            user.setCitiesProviding(cities);
            user.setProfileImage(profileImage);

            repo.save(user);

            return ResponseEntity.ok(new OwnerResponse(user));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }




    public ResponseEntity<?> updateProfileImage(Long userId, MultipartFile file) {
        Users user = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String uploadDir = "uploads/owners/" + userId + "/";
        try {
            Files.createDirectories(Paths.get(uploadDir));
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            Image profileImage = new Image();
            profileImage.setFilePath(path.toString());
            profileImage.setOwner(user);

            user.setProfileImage(profileImage);
            repo.save(user);

            return ResponseEntity.ok("Profile image updated successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Image upload failed: " + e.getMessage());
        }
    }


    public OwnerResponse getOwnerById(Long id) {
        Users user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        return new OwnerResponse(user);
    }
}
