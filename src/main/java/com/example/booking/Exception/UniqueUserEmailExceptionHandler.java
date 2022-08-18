package com.example.booking.Exception;

import com.example.booking.repositories.UserRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueUserEmailExceptionHandler implements ConstraintValidator<UniqueUserEmail,String>  {
    private  final UserRepository userRepository;

    public UniqueUserEmailExceptionHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.existsUserByEmail(email);
    }
}
