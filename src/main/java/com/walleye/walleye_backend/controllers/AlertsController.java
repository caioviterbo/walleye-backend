package com.walleye.walleye_backend.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.walleye.walleye_backend.dto.AlertsReceiveDto;
import com.walleye.walleye_backend.entities.Alerta;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.services.AlertsService;
import com.walleye.walleye_backend.services.EmailService;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;



@RequestMapping("/alerts")
@RestController
@AllArgsConstructor
public class AlertsController {

    private final AlertsService alertsService;
    
    @PostMapping(value = "/create", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Alerta> alertReceiver(
        @RequestPart("dto") AlertsReceiveDto dto, 
        @RequestPart(value = "file", required = false) MultipartFile file) throws MessagingException {
        Alerta alerta = alertsService.createAlerts(dto, file);
        return ResponseEntity.ok(alerta);
    }
    

}
