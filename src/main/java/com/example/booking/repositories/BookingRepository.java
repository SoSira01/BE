package com.example.booking.repositories;

import com.example.booking.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findBycategoryIdOrderByStartTimeDesc(Integer categoryId);
    List<Booking> findByEmailAndCategoryIdOrderByStartTimeDesc(String email,Integer category);
    List<Booking> findBookingByEmailOrderByStartTimeDesc(String userEmail);
    List<Booking> findByIdAndEmail(Integer bid, String email);
    List<Booking> findByEmail(String email);
    Booking findAllById(Integer bid);
    Booking findByFileName(String name);
    List<Booking> findByCategoryIdAndStartTime(Integer cateId, Date date);

}
