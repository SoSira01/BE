package com.example.booking.services;

import com.example.booking.Enum.Role;
import com.example.booking.dtos.*;
import com.example.booking.entities.User;
import com.example.booking.repositories.UserRepository;
import com.example.booking.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final PasswordService passwordService;

    public UserService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    //get all
    public List<UserDTO> getUser() {
        List<User> user = repository.findAll(Sort.by("name").ascending());
        return listMapper.mapList(user, UserDTO.class, modelMapper);
    }

    //get user by id
    public UserByIdDTO getUserById(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User Id "+ userId+
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(user, UserByIdDTO.class);
    }
    //Create
    public User create(AddUserDTO newUser) {
        User user = modelMapper.map(newUser, User.class);
        user.setPassword(passwordService.securePassword(user.getPassword()));
        return repository.saveAndFlush(user);
    }
    //EDIT
    public User mapUser(User existsUser, EditUserDTO edituserdto){
        if(edituserdto.getName() != null){
            existsUser.setName(edituserdto.getName().trim());
        }
        if(edituserdto.getEmail() != null){
            existsUser.setEmail(edituserdto.getEmail());
        }
        if(edituserdto.getRole() != null){
            existsUser.setRole(Role.valueOf(edituserdto.getRole()));
        }
        return existsUser;
    }

    public EditUserDTO editUser(EditUserDTO edituserdto, Integer id) {
        User user = repository.findById(id).map(o->mapUser(o, edituserdto))
                .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "No ID : " + id));
        repository.saveAndFlush(user);
        return modelMapper.map(user, EditUserDTO.class);
    }

    //delete booking
    public void deleteById(Integer id) {
        repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + "Not Found ID To Delete"));
        repository.deleteById(id);
    }

}
