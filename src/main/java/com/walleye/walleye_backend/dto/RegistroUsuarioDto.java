package com.walleye.walleye_backend.dto;

import lombok.Data;

@Data
public class RegistroUsuarioDto {
    
    private String nome;

    private String email;

    private String senha;
}
