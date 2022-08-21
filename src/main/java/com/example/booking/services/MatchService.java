package com.example.booking.services;

import com.example.booking.Exception.BookExceptionModel;
import com.example.booking.Exception.BookFieldError;
import com.example.booking.dtos.MatchUserDTO;
import com.example.booking.entities.User;
import com.example.booking.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Getter
@Setter
@Service
public class MatchService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordService passwordService;

    @Autowired
    public MatchService(UserRepository repository, ModelMapper modelMapper, PasswordService passwordService) {
        this.repository = repository;
        this.modelMapper = modelMapper;
        this.passwordService = passwordService;
    }

    public ResponseEntity<Object> match(MatchUserDTO newMatch){
        BookFieldError fieldError;
        BookExceptionModel bookExceptionModel;

        try{
            User user = repository.findByEmail(newMatch.getEmail());
            Object obj = modelMapper.map(user,MatchUserDTO.class);

            boolean matchPass = passwordService.validatePassword(user.getPassword(),newMatch.getPassword());

            if(obj !=null && matchPass){
                return new ResponseEntity<>("Password Matched", HttpStatus.OK);
            }else{
                fieldError = new BookFieldError("Password","Password NOT Matched",HttpStatus.UNAUTHORIZED);
                bookExceptionModel = new BookExceptionModel(fieldError.getStatus(),"Validation failed",fieldError);
                return new ResponseEntity<>(bookExceptionModel,fieldError.getStatus());
            }

        }catch (Exception e){
            fieldError = new BookFieldError("Email","A user with the specified email DOES NOT exist",HttpStatus.NOT_FOUND);
            bookExceptionModel = new BookExceptionModel(fieldError.getStatus(),"Validation failed",fieldError);
            return new ResponseEntity<>(bookExceptionModel,fieldError.getStatus());
        }
    }

}
