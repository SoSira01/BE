package com.example.booking.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.validation.constraints.Email;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class BookingWithFileDTO {
    private Integer id;
    @NotNull
    @Size(min = 1, max = 100 ,  message = "Booking name must be between 1 to 100 characters")
    private String bookingName;
    @NotNull
    @FutureOrPresent
    private Date startTime;
    @NotNull
    @Size(min = 1, max = 100)
    @Email(message = "Must be well-formed as email address")
    private String email;
    @Size(max = 500 ,message = "Notes must be lower than 500 or equal characters")
    private String note;
    @NotNull
    private Integer categoryId;
    private String categoryName;
    private Integer categoryDuration;
    private String categoryDescription;

    private String fileName;
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String fileURL;
    private String fileType;
    private byte[] fileByte;

}