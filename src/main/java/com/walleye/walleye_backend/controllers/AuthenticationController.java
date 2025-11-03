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

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/auth")
@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/registro")
    public ResponseEntity<Usuario> register(@RequestBody RegistroUsuarioDto registroUsuarioDto) {
        Usuario registroUsuario = authenticationService.signup(registroUsuarioDto);

        return ResponseEntity.ok(registroUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUsuarioDto loginUsuarioDto) {
        System.out.println(">>> Entrou no login controller");

        Usuario authenticatedUser = authenticationService.authenticate(loginUsuarioDto);
        System.out.println(">>> Resultado do authenticate(): " + authenticatedUser);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        System.out.println(">>> Response final: " + loginResponse);

        return ResponseEntity.ok(loginResponse);

    }

    @GetMapping("/me")
    public ResponseEntity<List<Usuario>> allUsers() {
        List <Usuario> users = authenticationService.allUsers();

        return ResponseEntity.ok(users);
    }
    
    
}
