package com.squad15.armariointeligente.util;

import com.squad15.armariointeligente.model.Usuario;
import com.squad15.armariointeligente.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new User(usuario.getEmail(), usuario.getSenha(),
                List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getTipoUsuario().getNome())));
    }
    
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}