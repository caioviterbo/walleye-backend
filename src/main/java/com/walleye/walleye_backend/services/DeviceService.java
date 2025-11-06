package com.walleye.walleye_backend.services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.walleye.walleye_backend.dto.AddDeviceDto;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.entities.Usuario;
import com.walleye.walleye_backend.repositories.DispositivoRepository;
import com.walleye.walleye_backend.util.PairCodeGenerator;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeviceService {

    private final DispositivoRepository dispositivoRepository;
    
    private final PairCodeGenerator codeGenerator;

    public Dispositivo addDevice(AddDeviceDto input, Usuario usuario) {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNome(input.getNome());
        dispositivo.setLocalizacao(input.getLocalização());
        dispositivo.setId_usuario(usuario);
        dispositivo.setData_registro(LocalDateTime.now());
        dispositivo.setCodigo_pareador(codeGenerator.generatePairCode());
        dispositivo.setPareado(false);

        return dispositivoRepository.save(dispositivo);

    }
    
}
