package com.example.booking.dtos;

import com.example.booking.Exception.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    @NotNull
    @UniqueUser
    @Size(min = 1, max = 100 ,  message = "name must be between 1 to 100 characters")
    private String name;

    @NotNull
    @UniqueUserEmail
    @Size(min = 1, max = 100)
    @Email(message = "Must be well-formed as email address")
    private String email;

    @NotNull
//    @EnumUserRole(regexp = "student | admin | lecturer")
    private String role;
}