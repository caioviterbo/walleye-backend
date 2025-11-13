package com.walleye.walleye_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.walleye.walleye_backend.dto.AddDeviceDto;
import com.walleye.walleye_backend.dto.PairDeviceDto;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.entities.Usuario;
import com.walleye.walleye_backend.services.DeviceService;

import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            System.out.println(">>> Entrou no addDevice controller");
            Dispositivo dispositivo = deviceService.addDevice(dispositivoDto, usuario);
        
            System.out.println(">>> Device" + dispositivo);
            return ResponseEntity.ok(dispositivo);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Dispositivo> editDevice(@PathVariable UUID id,
        @RequestBody AddDeviceDto dto) {
        System.out.println(">>> Entrou no editDevice controller");
        Dispositivo dispositivo = deviceService.editDevice(id, dto);
        System.out.println(">>> Device" + dispositivo);
        return ResponseEntity.ok(dispositivo);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteDevice(@PathVariable UUID id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.ok().build();
    }
    

    @PostMapping("/pair")
    @PermitAll
    public ResponseEntity<?> pairDevice(@RequestBody PairDeviceDto dto) {
        System.out.println(">>> ENTROU NO /device/pair com body: " + dto);
        Dispositivo dispositivo = deviceService.pairDevice(dto);
        return ResponseEntity.ok(dispositivo);
    }
    

    
}
