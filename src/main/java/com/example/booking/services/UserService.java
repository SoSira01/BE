package com.example.booking.services;

import com.example.booking.dtos.UserDTO;
import com.example.booking.entities.User;
import com.example.booking.repositories.UserRepository;
import com.example.booking.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    //get all
    public List<UserDTO> getUser() {
        List<User> user = repository.findAll();
        return listMapper.mapList(user, UserDTO.class, modelMapper);
    }

    //get user by id
    public UserDTO getUserById(Integer userId) {
        User user = repository.findById(userId)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "category id "+ userId+
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(user, UserDTO.class);
    }
    //Create
    public User create(UserDTO newUser) {
        User user = modelMapper.map(newUser, User.class);
        return repository.saveAndFlush(user);
    }
    //Edit
    public UserDTO editUser(UserDTO edituserdto, Integer id){
        User user = modelMapper.map(edituserdto, User.class);
        User u = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Booking id" + id +
                        "Not found ID to Edit"
                ));
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setRole(user.getRole());

        repository.saveAndFlush(u);
        return modelMapper.map(u,UserDTO.class);
    }
    //delete booking
    public void deleteById(Integer id) {
        repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + "Not Found ID To Delete"));
        repository.deleteById(id);
    }
}
