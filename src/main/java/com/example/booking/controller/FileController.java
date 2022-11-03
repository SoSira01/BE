package com.example.booking.controller;

import com.example.booking.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private BookingService bookingservices;

    //get files
    @GetMapping("/{id}/{name}")
    public ResponseEntity<?> getFile(HttpServletRequest request, @PathVariable("id") Integer id, @PathVariable("name") String name) {
        return bookingservices.getFile(request,name,id);
    }

    @GetMapping("/view/{id}/{name}")
    public ResponseEntity<?> viewFile(HttpServletRequest request, @PathVariable("id") Integer id, @PathVariable("name") String name) {
        return bookingservices.viewFile(request,name,id);
    }
}