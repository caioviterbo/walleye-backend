package com.walleye.walleye_backend.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.walleye.walleye_backend.entities.Dispositivo;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, UUID> {

    
} 