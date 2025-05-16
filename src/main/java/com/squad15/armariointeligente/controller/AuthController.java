package com.squad15.armariointeligente.controller;

import com.squad15.armariointeligente.dto.JwtAuthResponse;
import com.squad15.armariointeligente.dto.LoginDto;
import com.squad15.armariointeligente.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
        JwtAuthResponse jwtAuthResponse = authService.login(loginDto);
        return ResponseEntity.ok(jwtAuthResponse);
    }
}