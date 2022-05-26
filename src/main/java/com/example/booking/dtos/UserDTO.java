package com.example.booking.dtos;

import com.example.booking.Exception.Unique;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    @NotNull
    @Unique
    private String name;

    @NotNull
    @Size(min = 1, max = 100)
    @Email(message = "Must be well-formed as email address")
    private String email;

    @NotNull
    private String role;
}