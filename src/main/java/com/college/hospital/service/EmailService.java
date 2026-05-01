package com.college.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String toEmail, String name) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Hospital Registration Successful");

        message.setText(
                "Hello " + name + ",\n\n" +
                        "Welcome to our Hospital Management System.\n" +
                        "Your registration has been successfully completed.\n\n" +
                        "Thank you!"
        );

        mailSender.send(message);
    }
}