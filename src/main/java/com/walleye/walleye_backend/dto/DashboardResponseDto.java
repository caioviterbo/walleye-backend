package com.walleye.walleye_backend.dto;

import java.util.List;

import com.walleye.walleye_backend.entities.Alerta;
import com.walleye.walleye_backend.entities.Dispositivo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {
    private int totalDispositivos;
    private int online;
    private int offline;
    private int totalAlertas;
    private int alertasAtivos;
    private List<Dispositivo> dispositivos;
    private List<Alerta> alertas;
}
