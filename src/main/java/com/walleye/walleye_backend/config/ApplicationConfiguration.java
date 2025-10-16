package com.walleye.walleye_backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.walleye.walleye_backend.repositories.UsuarioRepository;


import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfiguration {

    private final UsuarioRepository usuarioRepository;
    
    
   @Bean 
   @Primary
   UserDetailsService userDetailsService() {
    return username -> usuarioRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado"));
   }

   @Bean
   BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticatorManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
   }

   @Bean
   AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                 BCryptPasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
   }
}
