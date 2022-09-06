package com.example.booking.controller;

import com.example.booking.dtos.MatchUserDTO;
import com.example.booking.services.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/match")
@AllArgsConstructor

public class MatchController {
    private final MatchService service;

    @PostMapping("")
    public ResponseEntity<Object> match(@RequestBody @Valid MatchUserDTO newMatch){
        return service.match(newMatch);
    }
}
