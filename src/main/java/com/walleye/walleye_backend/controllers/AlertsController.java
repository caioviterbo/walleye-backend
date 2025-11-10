package com.walleye.walleye_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.walleye.walleye_backend.dto.AlertsReceiveDto;
import com.walleye.walleye_backend.entities.Alerta;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.services.AlertsService;
import com.walleye.walleye_backend.services.EmailService;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/alerts")
@RestController
@AllArgsConstructor
public class AlertsController {

    private final AlertsService alertsService;
    
    @PostMapping("/create")
    public ResponseEntity<Alerta> alertReceiver(
        @RequestBody AlertsReceiveDto dto) throws MessagingException {
        Alerta alerta = alertsService.createAlerts(dto);
        return ResponseEntity.ok(alerta);
    }
    

}
