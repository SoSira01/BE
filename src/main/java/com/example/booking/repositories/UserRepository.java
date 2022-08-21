package com.example.booking.repositories;

import com.example.booking.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Integer> {
    boolean existsUserByEmail(String email);
    boolean existsUserByName(String name);
    User findByEmail(String email);

}
