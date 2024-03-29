package com.example.booking.entities;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
@Getter
@Setter
@Data
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId", nullable = false)
    private Integer id;

    @Column(name = "CategoryName", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "Duration", nullable = false, length = 45)
    private Integer duration;

    @Column(name = "CategoryDescription", nullable = false, length = 500)
    private String categoryDescription;

    @OneToMany(mappedBy = "category")
    private Set<Booking> bookings = new LinkedHashSet<>();

    @OneToMany(mappedBy = "eventCategory", cascade = CascadeType.ALL)
    private Set<CategoryOwner> categoryOwners;
}