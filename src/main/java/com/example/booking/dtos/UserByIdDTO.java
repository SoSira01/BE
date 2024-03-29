package com.example.booking.dtos;

import com.example.booking.Enum.Role;
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
    private String name;
    private String email;
    private String role;
    private Date createdOn;
    private Date updatedOn;
}
