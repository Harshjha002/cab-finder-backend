package com.example.cabfinder.controllers;


import com.example.cabfinder.Requests.CreateCabRequest;
import com.example.cabfinder.Response.CabResponse;
import com.example.cabfinder.Response.CreateCabResponse;
import com.example.cabfinder.Response.GetUserByIdResponse;
import com.example.cabfinder.models.Cab;
import com.example.cabfinder.service.CabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cab")
public class CabController {

    @Autowired
    private CabService service;

    @GetMapping("/{id}")
    public ResponseEntity<CabResponse> findCab(@PathVariable Long id) {
        return service.findCabById(id);
    }

    @PostMapping("{id}/add-cab")
    public ResponseEntity<CreateCabResponse> addCab(@PathVariable long id, @RequestBody CreateCabRequest request){
        return service.addCab(id,request);
    }
}
