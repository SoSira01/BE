package com.example.booking.dtos;
import com.example.booking.Exception.UniqueCategory;
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
    @NotNull
    @UniqueCategory
    private String categoryName;
    @NotNull
    @Max(480)
    @Min(1)
    private Integer duration;
    private String categoryDescription;

}
