package com.example.cabfinder.controllers;

import com.example.cabfinder.Requests.*;
import com.example.cabfinder.Response.*;
import com.example.cabfinder.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * UserController handles all user-related operations such as registration,
 * authentication, profile updates, and owner upgrades.
 */
@RestController
@RequestMapping("api/user")
@CrossOrigin("http://localhost:5173/")
public class UserController {

    @Autowired
    private UserService service;

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id ID of the user.
     * @return User details wrapped in a GetUserByIdResponse.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetUserByIdResponse> findUser(@PathVariable Long id) {
        return service.findUserById(id);
    }

    /**
     * Updates user details such as name, email, or password.
     *
     * @param id      ID of the user to update.
     * @param request DTO containing fields to be updated.
     * @return Updated user information.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {
        return service.updateUser(id, request);
    }

    /**
     * Registers a new user in the system.
     *
     * @param request DTO containing registration data (name, email, password, etc.).
     * @return Success message and basic user data upon registration.
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody RegisterUserRequest request) {
        return service.register(request);
    }

    /**
     * Authenticates a user using email and password credentials.
     *
     * @param request DTO containing login credentials.
     * @return Auth token and user data upon successful login.
     */
    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signin(@RequestBody SignInRequest request) {
        return service.signin(request);
    }

    /**
     * Upgrades a regular user to an owner by uploading a profile image
     * and providing the list of cities they operate in.
     *
     * @param userId       ID of the user upgrading to owner.
     * @param profileImage Multipart image file (owner's profile).
     * @param citiesJson   JSON string representing list of cities.
     * @return Success response after owner upgrade.
     */
    @PostMapping("/{userId}/become-owner")
    public ResponseEntity<?> upgradeToOwner(
            @PathVariable Long userId,
            @RequestParam("profileImage") MultipartFile profileImage,
            @RequestParam("cities") String citiesJson) {
        return service.upgradeToOwner(userId, profileImage, citiesJson);
    }

    /**
     * Allows the user to update their profile image separately.
     *
     * @param userId ID of the user.
     * @param file   Multipart image file (new profile image).
     * @return Success response with updated image info.
     */
    @PostMapping("/{userId}/update-profile-image")
    public ResponseEntity<?> updateProfileImage(
            @PathVariable Long userId,
            @RequestPart("file") MultipartFile file) {
        return service.updateProfileImage(userId, file);
    }

    /**
     * Retrieves public information of an owner (for cab display/contact pages).
     *
     * @param id ID of the owner.
     * @return Owner profile info, including contact and city coverage.
     */
    @GetMapping("/owner/{id}")
    public ResponseEntity<OwnerResponse> getOwner(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOwnerById(id));
    }
}
