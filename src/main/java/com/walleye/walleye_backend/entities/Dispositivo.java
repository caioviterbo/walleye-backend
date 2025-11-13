package com.walleye.walleye_backend.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "dispositivos")
@Entity
@Data
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    private String nome;
    private String localizacao;
    private String status;
    private LocalDateTime ultima_atualizacao;
    private String rachaduras_detectadas;
    private LocalDateTime data_registro;
    private String codigo_pareador;
    private Boolean pareado;

    @JoinColumn(name = "expira_em")
    private LocalDateTime expiraEm;

    
}
