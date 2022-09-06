package com.example.booking.controller;

import com.example.booking.dtos.*;
import com.example.booking.entities.User;
import com.example.booking.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    //get all
    @GetMapping("")
    public List<UserDTO> getAllUsers(){
        return  userservices.getUser();
    }
    @Autowired
    private UserService userservices;
    //get by id
    @GetMapping("/{id}")
    public UserByIdDTO getUserById(@PathVariable Integer id){
        return  userservices.getUserById(id);
    }

    //create
    @PostMapping("")
    public User create(@Valid @RequestBody AddUserDTO newUser){
        return userservices.create(newUser);
    }

    //delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        userservices.deleteById(id);
    }

    //edit
    @PatchMapping("/{id}")
    public EditUserDTO editBooking(@Valid  @RequestBody EditUserDTO edituserdto, @PathVariable Integer id){
        return userservices.editUser(edituserdto ,id);
    }
}
