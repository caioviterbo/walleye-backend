package com.walleye.walleye_backend.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.walleye.walleye_backend.dto.AlertsReceiveDto;
import com.walleye.walleye_backend.entities.Alerta;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.repositories.AlertaRepository;
import com.walleye.walleye_backend.repositories.DispositivoRepository;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AlertsService {
    
    private final AlertaRepository alertaRepository;

    private final DispositivoRepository dispositivoRepository;

    private final EmailService emailService;

    public Alerta createAlerts(AlertsReceiveDto dto) {
        Dispositivo dispositivo = dispositivoRepository.findById(dto.getId_dispositivo())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dispositivo não encontrado"));
        Alerta alerta = new Alerta();
        alerta.setData_deteccao(dto.getDate_detection());
        alerta.setSeveridade(dto.getSeverity());
        alerta.setMensagem(dto.getMessage());
        alerta.setId_dispositivo(dispositivo);
        alerta.setResolvido(false);

        try {
           if (dto.getSeverity().equalsIgnoreCase("alta") || dto.getSeverity().equalsIgnoreCase("crítica")) {
            emailService.sendEmail(dispositivo.getId_usuario(), dispositivo, alerta);
        }
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return alertaRepository.save(alerta);
    }
}
