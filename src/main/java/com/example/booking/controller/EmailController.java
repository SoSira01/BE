package com.example.booking.controller;
import com.example.booking.email.EmailService;
import com.example.booking.entities.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired private EmailService emailService;

    // Sending a simple Email
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody Email details)
    {
        String status = emailService.sendSimpleMail(details);
        return status;
    }

    // Sending email with attachment
    @PostMapping("/sendMailWithAttachment")
    public String sendMailWithAttachment(@RequestBody Email details)
    {
        String status = emailService.sendMailWithAttachment(details);
        return status;
    }

    // Set sending a email to contact admin
    @PostMapping("/sendProblemMail")
    public String sendToAdminMail(@RequestBody Email details)
    {
        String status = emailService.sendToAdminMail(details);
        return status;
    }
}
