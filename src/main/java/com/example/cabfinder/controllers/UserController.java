package com.example.cabfinder.controllers;

import com.example.cabfinder.Requests.*;
import com.example.cabfinder.Response.*;
import com.example.cabfinder.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/user")
@CrossOrigin("http://localhost:5173/")
public class UserController {

    @Autowired
    private UserService service;

    // 🔍 Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdResponse> findUser(@PathVariable Long id) {
        return service.findUserById(id);
    }

    // ✏️ Update user details
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {
        return service.updateUser(id, request);
    }

    // 📝 Register new user
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request) {
        return service.register(request);
    }

    // 🔐 Sign in
    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signin(@RequestBody SignInRequest request) {
        return service.signin(request);
    }

    // 📸 Upload profile image + cities (owner flow)
    @PostMapping("/{userId}/become-owner")
    public ResponseEntity<?> upgradeToOwner(
            @PathVariable Long userId,
            @RequestParam("profileImage") MultipartFile profileImage,
            @RequestParam("cities") String citiesJson) {
        return service.upgradeToOwner(userId, profileImage, citiesJson);
    }

    // 📸 Update profile image separately
    @PostMapping("/{userId}/update-profile-image")
    public ResponseEntity<?> updateProfileImage(
            @PathVariable Long userId,
            @RequestPart("file") MultipartFile file) {
        return service.updateProfileImage(userId, file);
    }

    // 👤 Get owner info
    @GetMapping("/owner/{id}")
    public ResponseEntity<OwnerResponse> getOwner(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOwnerById(id));
    }
}
