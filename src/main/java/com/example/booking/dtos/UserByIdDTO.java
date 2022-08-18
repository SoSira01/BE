package com.example.booking.dtos;

import com.example.booking.Exception.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserByIdDTO {
    private Integer id;

    @NotNull
    @UniqueUser
    private String name;

    @NotNull
    @UniqueUserEmail
    @Size(min = 1, max = 100)
    @Email(message = "Must be well-formed as email address")
    private String email;

    @NotNull
//    @EnumUserRole(regexp = "student|admin|lecturer")
    private String role;

    private Date createdOn;

    private Date updatedOn;
}
