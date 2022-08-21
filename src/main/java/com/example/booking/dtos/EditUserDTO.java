package com.example.booking.dtos;

import com.example.booking.Enum.Role;
import com.example.booking.Exception.EnumUserRole;
import com.example.booking.Exception.UniqueUser;
import com.example.booking.Exception.UniqueUserEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditUserDTO {
    private Integer id;

    @Size(min = 1, max = 100 ,  message = "name must be between 1 to 100 characters")
    @UniqueUser
    private String name;

    @Size(min = 1, max = 50 , message = "email must be between 1 to 50 characters")
    @Email(message = "Must be well-formed as email address")
    @UniqueUserEmail
    private String email;

    @EnumUserRole(enumClass = Role.class)
    private String role;


}
