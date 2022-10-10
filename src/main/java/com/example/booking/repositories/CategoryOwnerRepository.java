package com.example.booking.repositories;

import com.example.booking.entities.CategoryOwner;
import com.example.booking.entities.CategoryOwnerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryOwnerRepository extends JpaRepository<CategoryOwner, CategoryOwnerId> {
    List<CategoryOwner> findByUser_Name(String uname);
}
