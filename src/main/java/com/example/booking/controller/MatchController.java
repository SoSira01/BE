package com.example.booking.controller;

import com.example.booking.dtos.MatchUserDTO;
import com.example.booking.services.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
