package com.walleye.walleye_backend.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.walleye.walleye_backend.dto.LoginUsuarioDto;
import com.walleye.walleye_backend.dto.RegistroUsuarioDto;
import com.walleye.walleye_backend.entities.Usuario;
import com.walleye.walleye_backend.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Usuario signup(RegistroUsuarioDto input) {
        Usuario usuario = new Usuario();
        usuario.setNome(input.getNome());
        usuario.setEmail(input.getEmail());
        usuario.setSenha_hash(passwordEncoder.encode(input.getSenha()));
        usuario.setData_criacao(LocalDateTime.now());

        return usuarioRepository.save(usuario);
                    
    }

    public Usuario authenticate(LoginUsuarioDto input) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getEmail(), 
                input.getSenha()
                )
        );
        return usuarioRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}
 