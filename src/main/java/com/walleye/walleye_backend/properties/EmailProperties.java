package com.walleye.walleye_backend.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "email")
@Data
public class EmailProperties {
    private String remetente;
    private List<String> destinatarios;
}
