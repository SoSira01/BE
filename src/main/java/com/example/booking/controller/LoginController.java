package com.example.booking.controller;

import com.example.booking.dtos.MatchUserDTO;
import com.example.booking.services.JwtUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LoginController {
    @Autowired
    private JwtUserService service;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody MatchUserDTO loginRequest) {
        return service.loginUser(loginRequest);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> getUserRefreshToken(@Valid HttpServletRequest request) {
        return service.getRefreshToken(request);
    }
}