package com.example.booking.entities;

// Importing required classes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// Annotations
@Data
@AllArgsConstructor
@NoArgsConstructor

// Class
public class Email {

    // Class data members
    private String recipientName;
    private String recipient;
    private String category;
    private Date startTime;
    private String note;
    private String attachment;

}