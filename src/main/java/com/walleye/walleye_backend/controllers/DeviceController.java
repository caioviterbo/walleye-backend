package com.walleye.walleye_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walleye.walleye_backend.dto.AddDeviceDto;
import com.walleye.walleye_backend.dto.PairDeviceDto;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.entities.Usuario;
import com.walleye.walleye_backend.services.DeviceService;

import jakarta.annotation.security.PermitAll;
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


    @PostMapping("/add")
    public ResponseEntity<Dispositivo> addDevice(
        @RequestBody AddDeviceDto dispositivoDto, 
        @AuthenticationPrincipal Usuario usuario) {
            System.out.println(">>> Entrou no device controller");
            Dispositivo dispositivo = deviceService.addDevice(dispositivoDto, usuario);
        
            System.out.println(">>> Device" + dispositivo);
            return ResponseEntity.ok(dispositivo);
    }

    @PostMapping("/pair")
    @PermitAll
    public ResponseEntity<?> pairDevice(@RequestBody PairDeviceDto dto) {
        System.out.println(">>> ENTROU NO /device/pair com body: " + dto);
        Dispositivo dispositivo = deviceService.pairDevice(dto);
        return ResponseEntity.ok(dispositivo);
}

    
}
