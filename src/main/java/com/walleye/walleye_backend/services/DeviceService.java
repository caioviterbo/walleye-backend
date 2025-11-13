package com.walleye.walleye_backend.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.walleye.walleye_backend.dto.AddDeviceDto;
import com.walleye.walleye_backend.dto.PairDeviceDto;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.entities.Usuario;
import com.walleye.walleye_backend.repositories.DispositivoRepository;
import com.walleye.walleye_backend.util.PairCodeGenerator;

import jakarta.persistence.EntityNotFoundException;
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
        dispositivo.setUsuario(usuario);
        dispositivo.setData_registro(LocalDateTime.now());
        dispositivo.setCodigo_pareador(codeGenerator.generatePairCode());
        dispositivo.setPareado(false);
        dispositivo.setExpiraEm(LocalDateTime.now().plusMinutes(2));

        return dispositivoRepository.save(dispositivo);

    }

    public Dispositivo editDevice(UUID id ,AddDeviceDto dto) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Dispositivo não encontrado: " + id));

        dispositivo.setNome(dto.getNome());
        dispositivo.setLocalizacao(dto.getLocalizacao());

        return dispositivoRepository.save(dispositivo);

    }

    public void deleteDevice(UUID id) {
        Dispositivo dispositivo = dispositivoRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Dispositivo não encontrado: " + id));

       dispositivoRepository.delete(dispositivo);
    }

    public Dispositivo pairDevice(PairDeviceDto dto) {
        System.out.println(">>> PairDeviceService: iniciando validação: " + dto);
        Dispositivo dispositivo = dispositivoRepository.findById(UUID.fromString(dto.getId()))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dispositivo não encontrado"));

        if (Boolean.TRUE.equals(dispositivo.getPareado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Dispositivo já pareado");
        }

        if (!dispositivo.getCodigo_pareador().equals(dto.getCodigo_pareador())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código pareador inválido");
        }

        if (dispositivo.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Código expirado");
        }
        System.out.println(">>> Dispositivo encontrado no banco: " + dispositivo.getId());

        dispositivo.setPareado(true);
        dispositivo.setCodigo_pareador(null);
        dispositivo.setExpiraEm(null);
        dispositivo.setStatus("ONLINE");

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
