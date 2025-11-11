package com.walleye.walleye_backend.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "alertas")
@Entity
@Data
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_dispositivo", nullable = false)
    private Dispositivo dispositivo;

    private String severidade;
    @Column(columnDefinition = "TEXT")
    private String url_imagem;
    private LocalDateTime data_deteccao;
    private String mensagem;
    private boolean resolvido;
    private LocalDateTime data_resolvido;
    
}
