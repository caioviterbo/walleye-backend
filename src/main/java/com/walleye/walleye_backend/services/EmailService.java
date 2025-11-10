package com.walleye.walleye_backend.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.walleye.walleye_backend.dto.AlertsReceiveDto;
import com.walleye.walleye_backend.entities.Alerta;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.entities.Usuario;
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
    private final TemplateEngine templateEngine;

    @Async
    public void sendEmail(Usuario usuario, Dispositivo dispositivo, Alerta alerta) throws MessagingException {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,  true);

            Context context = new Context();
            context.setVariable("usuarioNome", usuario.getNome());
            context.setVariable("dispositivoNome", dispositivo.getNome());
            context.setVariable("localizacao", dispositivo.getLocalizacao());
            context.setVariable("horarioDeteccao", alerta.getData_deteccao());
            context.setVariable("nivelGravidade", alerta.getSeveridade());
            context.setVariable("descricao", alerta.getMensagem());
            context.setVariable("linkDashboard", "https://app.walleye.com/dashboard");

            String htmlContent = templateEngine.process("alerta-rachadura.html", context);

            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(usuario.getEmail());
            helper.setSubject("TESTE NOTIFICAÇÃO WALLEYE");
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
            System.out.println("E-mail enviado com sucesso para: " + usuario.getEmail());
        } catch (Exception e) {
             System.err.println("Erro ao enviar e-mail: " + e.getMessage());
             e.printStackTrace();
        }
    }
}
