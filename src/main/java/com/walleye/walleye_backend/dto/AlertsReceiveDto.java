package com.walleye.walleye_backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class AlertsReceiveDto {
    private UUID id_dispositivo;
    private LocalDateTime date_detection;
    private String severity;
    private String message;
    
}
