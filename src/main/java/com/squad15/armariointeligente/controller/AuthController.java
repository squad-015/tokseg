package com.squad15.armariointeligente.controller;

import com.squad15.armariointeligente.dto.RegistroUsuarioDTO;
import com.squad15.armariointeligente.model.TipoUsuario;
import com.squad15.armariointeligente.model.Usuario;
import com.squad15.armariointeligente.repository.TipoUsuarioRepository;
import com.squad15.armariointeligente.repository.UsuarioRepository;
import com.squad15.armariointeligente.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    // Classe auxiliar para representar o corpo da requisição
    public static class LoginRequest {
        public String email;
        public String password;
    }

    // Classe auxiliar para resposta com token
    public static class AuthResponse {
        public String token;

        public AuthResponse(String token) {
            this.token = token;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email, request.password));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos");
        }

        // Busca o usuário diretamente do repositório
        Usuario usuario = usuarioRepository.findByEmail(request.email).orElseThrow();
        final String jwt = jwtUtil.gerarToken(usuario);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistroUsuarioDTO registroDTO) {
        // Verifica se o usuário já existe
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email já existe");
        }

        // Busca o tipo de usuário pelo ID
        TipoUsuario tipoUsuario =
                tipoUsuarioRepository.findById(registroDTO.getTipoUsuarioId()).orElse(null);

        if (tipoUsuario == null) {
            return ResponseEntity.badRequest().body("Tipo de usuário não encontrado");
        }

        // Cria o objeto Usuario
        Usuario usuario = new Usuario();
        usuario.setNome(registroDTO.getNome().trim());
        usuario.setEmail(registroDTO.getEmail().toLowerCase());
        usuario.setSenha(encoder.encode(registroDTO.getSenha()));
        usuario.setTelefone(registroDTO.getTelefone().trim());
        usuario.setTipoUsuario(tipoUsuario);

        // Salva o novo usuário
        Usuario novoUsuario = usuarioRepository.save(usuario);

        // Gera o token JWT
        final String jwt = jwtUtil.gerarToken(novoUsuario);

        return ResponseEntity.status(201).body(new AuthResponse(jwt));
    }
}
