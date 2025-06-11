package com.squad15.armariointeligente.service;

import com.squad15.armariointeligente.model.Usuario;
import com.squad15.armariointeligente.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

// Removendo a anotação @Service para evitar conflito com CustomUserDetailsService
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        
        // Simplificar para depuração
        return new User(usuario.getEmail(), usuario.getSenha(), 
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}