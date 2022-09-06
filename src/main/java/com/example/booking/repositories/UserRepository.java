package com.example.booking.repositories;

import com.example.booking.entities.User;
import com.example.booking.Enum.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Integer> {

    boolean existsUserByEmail(String email);
    boolean existsUserByName(String name);
    User findByEmail(String email);
    User findByRole(Role role);

}
