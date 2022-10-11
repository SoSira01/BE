package com.example.booking.email;

// Importing required classes
import com.example.booking.Enum.Role;
import com.example.booking.entities.Booking;
import com.example.booking.entities.Email;
import java.io.File;
import java.util.Date;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.example.booking.entities.User;
import com.example.booking.repositories.BookingRepository;
import com.example.booking.repositories.UserRepository;
import com.example.booking.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

// Annotation
@Service
// Class
// Implementing EmailService interface
public class EmailServiceImpl implements EmailService {

    private String msgBody;
    @Autowired private JavaMailSender javaMailSender;
    @Autowired private UserService userService;
    @Autowired private BookingRepository bookingRepository;
    @Autowired private UserRepository userRepository;

    @Value("${spring.mail.username}") private String sender;
    private String setEmailSubject(Email userData) {
        return "[OASIP] " + userData.getCategory() + " @ " + userData.getStartTime();
    }

    public String setMsgBody(Email userData) {
        // Setting up necessary details
        User admin = userRepository.findByRole(Role.admin);
        this.msgBody = 

                // "To : "+userData.getRecipientName()+", "+userData.getRecipient()+
                " \n Your Booking Comfirmation : \n " +" \n Booking Name : "+userData.getRecipientName()+" \n Email : "+userData.getRecipient() +
                " \n Event Category : "+userData.getCategory() +" \n When : "+userData.getStartTime() +" \n Note : "+userData.getNote();
                // "\n \n If you found any failure on your booking confirmation, " +"\n please contract our admin for editing your request" +"\n Contract us : "+admin.getEmail();
        return msgBody;
    }

    // Method 1
    // To send a simple email
    public String sendSimpleMail(Email details)
    {
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setSubject(setEmailSubject(details));
            mailMessage.setReplyTo("noreply@intproj21.sit.kmutt.ac.th");
            mailMessage.setText(setMsgBody(details));
            System.out.println(setMsgBody(details));
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail : "+e;
        }
    }

    // Method 2
    // To send an email with attachment
    public String
    sendMailWithAttachment(Email details)
    {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            // Setting multipart as true for attachments to be send
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setSubject(setEmailSubject(details));
            mimeMessageHelper.setText(setMsgBody(details));
            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
        // Catch block to handle MessagingException
        catch (MessagingException e) {
            // Display message when exception occurred
            return "Error while sending mail! : "+e;
        }
    }
}
