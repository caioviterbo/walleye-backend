package com.walleye.walleye_backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walleye.walleye_backend.entities.Alerta;
import com.walleye.walleye_backend.entities.Dispositivo;

import java.util.List;


@Repository
public interface AlertaRepository extends JpaRepository<Alerta, UUID> {

    List<Alerta> findByDispositivoIn(List<Dispositivo> dispositivos);


} 