package com.example.booking.dtos;

import com.example.booking.Enum.Role;
import com.example.booking.Exception.*;
import lombok.*;

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
    @Size(min = 1, max = 50 , message = "email must be between 1 to 50 characters")
    @Email(message = "Must be well-formed as email address")
    private String email;

    @EnumUserRole(enumClass = Role.class)
    private String role;
}