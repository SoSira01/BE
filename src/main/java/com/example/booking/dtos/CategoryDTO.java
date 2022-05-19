package com.example.booking.dtos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Integer id;

    private String categoryName;
    @NotNull
    @Size(min = 1 ,max = 480 ,message = "duration must be lower than 480 or equal characters")
    private Integer duration;
    private String categoryDescription;

}
