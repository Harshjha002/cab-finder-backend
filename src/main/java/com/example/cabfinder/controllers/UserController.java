package com.example.cabfinder.controllers;

import com.example.cabfinder.Requests.*;
import com.example.cabfinder.Response.*;
import com.example.cabfinder.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestBody UpdateUserRequest request
    ) {
        return service.updateUser(id, request);
    }

    // 🆙 Upgrade user to owner
    @PutMapping("/{id}/upgrade-to-owner")
    public ResponseEntity<SimpleApiResponse> updateToOwner(@PathVariable long id) {
        return service.updateToOwner(id);
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
}
