package com.squad15.armariointeligente.service;

import com.squad15.armariointeligente.dto.LoginDto;
import com.squad15.armariointeligente.dto.JwtAuthResponse;
import com.squad15.armariointeligente.model.Usuario;
import com.squad15.armariointeligente.repository.UsuarioRepository;
import com.squad15.armariointeligente.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.email(),
                        loginDto.senha()
                )
        );
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        Usuario usuario = usuarioRepository.findByEmail(loginDto.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        String token = jwtService.generateToken(usuario);
        
        return new JwtAuthResponse(token);
    }
}