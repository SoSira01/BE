package com.example.booking.email;

// Importing required classes
import com.example.booking.entities.Email;

// Interface
public interface EmailService {

    // Method
    // To send a simple email
    String sendSimpleMail(Email details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(Email details);
}