package com.walleye.walleye_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walleye.walleye_backend.services.EmailService;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/email")
@AllArgsConstructor
public class EmailController {
    
    private final EmailService emailService;
    

    @GetMapping("/teste")
    public String enviarEmail() throws MessagingException {
        emailService.sendEmail();
        return "email enviado";
    }
    

}
