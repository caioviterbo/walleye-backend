package com.walleye.walleye_backend.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        dispositivo.setLocalizacao(input.getLocalizacao());
        dispositivo.setId_usuario(usuario);
        dispositivo.setData_registro(LocalDateTime.now());
        dispositivo.setCodigo_pareador(codeGenerator.generatePairCode());
        dispositivo.setPareado(false);
        dispositivo.setExpiraEm(LocalDateTime.now().plusMinutes(2));

        return dispositivoRepository.save(dispositivo);

    }
    
    @Transactional
    public void removerDispositivosNaoPareadosExpirados() {
        System.out.println("[SCHEDULER] Verificando dispositivos expirados...");

        Long removidos = dispositivoRepository
            .deleteByPareadoFalseAndExpiraEmBefore(LocalDateTime.now());

        long totalRemovidos = (removidos != null) ? removidos : 0L;

        if (totalRemovidos > 0) {
            System.out.println("[SCHEDULER] Removidos " + totalRemovidos + " dispositivos expirados.");
        } else {
            System.out.println("[SCHEDULER] Nenhum dispositivo expirado encontrado.");
        }
    }
    
}
