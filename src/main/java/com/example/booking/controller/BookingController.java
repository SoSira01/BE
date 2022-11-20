package com.example.booking.controller;

import com.example.booking.Jwt.JwtTokenUtil;
import com.example.booking.dtos.BookingDTO;
import com.example.booking.dtos.EditBookingDTO;
import com.example.booking.entities.Booking;
import com.example.booking.entities.User;
import com.example.booking.repositories.UserRepository;
import com.example.booking.services.BookingService;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/booking")
public class BookingController {
    //get all working
    @GetMapping("")
    public List<BookingDTO> getAllBooking(HttpServletRequest request){
        return  bookingservices.getBooking(request);
    }
    @Autowired
    private BookingService bookingservices;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
    //get by id working
    @GetMapping("/{id}")
    public BookingDTO getBookingById(HttpServletRequest request,@PathVariable Integer id){
        return  bookingservices.getBookingById(request,id);
    }
    //get by categoryId
    @GetMapping("/filter/{categoryId}")
    public List<BookingDTO> getBookingByCategoryId(HttpServletRequest request,@PathVariable Integer categoryId){
        return bookingservices.getBookingByCategoryId(request,categoryId);
    }
    //create booking
    @PostMapping("")
    @RequestMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Booking create(HttpServletRequest request, 
        @Valid @RequestPart("booking") BookingDTO newBooking, 
        @RequestPart(value = "file",required = false) @Nullable MultipartFile file){

        if(file == null){
            return bookingservices.create(request,newBooking);
        }
        return bookingservices.createWithFile(request,newBooking,file);
    }

    //delete booking
    @DeleteMapping("/{id}")
    public void deleteById(HttpServletRequest request,@PathVariable Integer id){
        bookingservices.deleteById(request,id);
    }
    //edit booking
    @PatchMapping("/{id}")
    public EditBookingDTO editBooking(HttpServletRequest request,@Valid @RequestPart("booking") EditBookingDTO editbookingdto,
                                      @PathVariable Integer id, @RequestPart(value = "file",required = false) @Nullable MultipartFile file){
        return bookingservices.editBooking(request,editbookingdto,id,file);
    }
    
}
