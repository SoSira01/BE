package com.example.booking.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchUserDTO {

    @Size(min = 1, max = 50 , message = "email must be between 1 to 50 characters")
    @Email(message = "Must be well-formed as email address")
    private String email;

    @NotNull
    @Size(min = 8, max = 14 , message = "password must be between 8 to 14 characters")
    private String password;
}
