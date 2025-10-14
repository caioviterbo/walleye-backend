package com.walleye.walleye_backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.walleye.walleye_backend.properties.EmailProperties;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;

    @Async
    public void sendEmail() throws MessagingException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,  true);

            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(emailProperties.getDestinatarios().toArray( new String[0]));
            helper.setSubject("TESTE NOTIFICAÇÃO WALLEYE");
            helper.setText("TESTANDO SERVIDOR SMTP DO TURBOSMTP");

            javaMailSender.send(mimeMessage);
            System.out.println("E-mail enviado com sucesso para: " + emailProperties.getDestinatarios());
        } catch (Exception e) {
             System.err.println("Erro ao enviar e-mail: " + e.getMessage());
             e.printStackTrace();
        }
    }
}
