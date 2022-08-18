package com.example.booking.Exception;

import com.example.booking.repositories.UserRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
@Component
public class UniqueUserExceptionHandler implements ConstraintValidator<UniqueUser,String> {
    private  final UserRepository userRepository;

    public UniqueUserExceptionHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return !userRepository.existsUserByName(name);
    }
}
