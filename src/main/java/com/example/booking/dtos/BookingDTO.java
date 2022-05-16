package com.example.booking.dtos;

import com.example.booking.entities.Category;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BookingDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Cat")
    private String bookingName;
    private Date startTime;
    private String email;
    private String note;
//    @JsonIgnore
    private Integer categoryId;
    private String categoryName;
    private Integer categoryDuration;
    private String categoryDescription;


}
