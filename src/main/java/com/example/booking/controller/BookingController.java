package com.example.booking.controller;

import com.example.booking.Jwt.JwtTokenUtil;
import com.example.booking.dtos.BookingDTO;
import com.example.booking.dtos.EditBookingDTO;
import com.example.booking.entities.Booking;
import com.example.booking.entities.User;
import com.example.booking.repositories.UserRepository;
import com.example.booking.services.BookingService;
import org.springframework.web.bind.annotation.*;
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
    public List<BookingDTO> getAllBooking(){
        return  bookingservices.getBooking();
    }
    @Autowired
    private BookingService bookingservices;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserRepository userRepository;
    //get by id working
    @GetMapping("/{id}")
    public BookingDTO getBookingById(@PathVariable Integer id){
        return  bookingservices.getBookingById(id);
    }
    //get by categoryId
    @GetMapping("/filter/{categoryId}")
    public List<BookingDTO> getBookingByCategoryId(@PathVariable Integer categoryId){
        return bookingservices.getBookingByCategoryId(categoryId);
    }
    //create booking
    @PostMapping("")
    public Booking create(@Valid @RequestBody BookingDTO newBooking){
        return bookingservices.create(newBooking);
    }
    //delete booking
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        bookingservices.deleteById(id);
    }
    //edit booking
    @PatchMapping("/{id}")
    public EditBookingDTO editBooking(@Valid  @RequestBody EditBookingDTO editbookingdto,@PathVariable Integer id){
        return bookingservices.editBooking(editbookingdto ,id);
    }
//    @GetMapping("")
//    public List<BookingDTO> getAllBooking(HttpServletRequest request){
//        String requestToken = request.getHeader("Authorization").substring(7);
//        String userName = jwtTokenUtil.getUsernameFromToken(requestToken);
//        User user = userRepository.findByEmail(userName);
//        if (user.getRole().name() == "student") {
//            return bookingservices.getBookingByEmail(request);
//        } if (user.getRole().name() == "lecturer") {
//            return bookingservices.getBooking();
//        } if (user.getRole().name() == "admin") {
//            return bookingservices.getBooking();
//        } else {
//            List<BookingDTO> emptyBooking = new ArrayList<>();
//            return emptyBooking;
//        }
//    }
}
