package com.walleye.walleye_backend.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.walleye.walleye_backend.dto.DashboardResponseDto;
import com.walleye.walleye_backend.entities.Alerta;
import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.repositories.AlertaRepository;
import com.walleye.walleye_backend.repositories.DispositivoRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@AllArgsConstructor
public class DashboardService {

    private final DispositivoRepository dispositivoRepository;
    private final AlertaRepository alertaRepository;

    public DashboardResponseDto getDashboard(UUID userId) {
        List<Dispositivo> dispositivos = dispositivoRepository.findByUsuarioId(userId);
        List<Alerta> alertas = alertaRepository.findByDispositivoIn(dispositivos);

        long online = dispositivos.stream()
            .filter(d -> "ONLINE".equalsIgnoreCase(d.getStatus())).count();
        long offline = dispositivos.size() - online;
        long ativos = alertas.stream().filter(a -> !a.isResolvido()).count();

        return new DashboardResponseDto(
            dispositivos.size(),
            (int) online,
            (int) offline,
            alertas.size(),
            (int) ativos,
            dispositivos,
            alertas
        );
    }
    
}
