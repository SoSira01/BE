package com.example.booking.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@Entity
@DynamicInsert
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false, insertable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "role", nullable = false, updatable = false)
    private String role;

    @Column(name = "createdOn", insertable = false, updatable = false)
    private Date createdOn;

    @Column(name = "updatedOn", insertable = false, updatable = false)
    private Date updatedOn;
}