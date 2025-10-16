package com.walleye.walleye_backend.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;

    private long expiresIn;

}
