package com.walleye.walleye_backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import com.fasterxml.jackson.databind.ext.Java7SupportImpl;


@Configuration
@EnableAsync
public class EmailConfig {

    private static final Logger logger = LoggerFactory.getLogger(EmailConfig.class);
    
   @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttls;

    @Bean
    public JavaMailSender javaMailSender() {

        logger.info("Configurando JavaMailSender...");
        logger.info("Host: {}", host);
        logger.info("Port: {}", port);
        logger.info("Username: {}", username);
        logger.info("Password (length): {}", password != null ? password.length() : "null");  // Cuidado com logs de senha!
        logger.info("Auth: {}", auth);
        logger.info("StartTLS: {}", starttls);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        java.util.Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.debug", "debug");

        logger.info("JavaMailSender configurado.");
        return mailSender;
    }
}
