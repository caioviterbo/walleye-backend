package com.walleye.walleye_backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.walleye.walleye_backend.dto.LoginResponse;
import com.walleye.walleye_backend.dto.LoginUsuarioDto;
import com.walleye.walleye_backend.dto.RegistroUsuarioDto;
import com.walleye.walleye_backend.entities.Usuario;
import com.walleye.walleye_backend.services.AuthenticationService;
import com.walleye.walleye_backend.services.JwtService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Usuario> register(@RequestBody RegistroUsuarioDto registroUsuarioDto) {
        Usuario registroUsuario = authenticationService.signup(registroUsuarioDto);

        return ResponseEntity.ok(registroUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUsuarioDto loginUsuarioDto) {
        Usuario authenticatedUser = authenticationService.authenticate(loginUsuarioDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
    
    
}
