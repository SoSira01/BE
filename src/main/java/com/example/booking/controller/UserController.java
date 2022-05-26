package com.example.booking.controller;

import com.example.booking.dtos.UserDTO;
import com.example.booking.entities.User;
import com.example.booking.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
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
    public UserDTO getUserById(@PathVariable Integer id){
        return  userservices.getUserById(id);
    }

    //create
    @PostMapping("")
    public User create(@Valid @RequestBody UserDTO newUser){
        return userservices.create(newUser);
    }

    //delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        userservices.deleteById(id);
    }

    //edit
    @PatchMapping("/{id}")
    public UserDTO editBooking(@Valid  @RequestBody UserDTO userdto, @PathVariable Integer id){
        return userservices.editUser(userdto ,id);
    }
}
