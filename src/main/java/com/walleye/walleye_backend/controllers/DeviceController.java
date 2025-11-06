package com.walleye.walleye_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walleye.walleye_backend.dto.AddDeviceDto;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.entities.Usuario;
import com.walleye.walleye_backend.services.DeviceService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/device")
@RestController
@AllArgsConstructor
public class DeviceController {
    
    private final DeviceService deviceService;

    @PostMapping("/addDevice")
    public ResponseEntity<Dispositivo> addDevice(
        @RequestBody AddDeviceDto dispositivoDto, 
        @AuthenticationPrincipal Usuario usuario) {

            Dispositivo dispositivo = deviceService.addDevice(dispositivoDto, usuario);
        
        
            return ResponseEntity.ok(dispositivo);
    }
    
}
