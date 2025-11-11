package com.walleye.walleye_backend.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walleye.walleye_backend.entities.Dispositivo;
import com.walleye.walleye_backend.entities.Usuario;
import java.util.List;


@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, UUID> {

    Long deleteByPareadoFalseAndExpiraEmBefore(LocalDateTime dateTime);
    List<Dispositivo> findByUsuarioId(UUID userId);
} 