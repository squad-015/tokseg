package com.squad15.armariointeligente.controller;

import com.squad15.armariointeligente.service.EmailEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email-entregas")
public class EmailEntregaController {

    @Autowired
    private EmailEntregaService emailEntregaService;

    @PostMapping("/enviar")
    public ResponseEntity<String> enviarEmails() {
        try {
            emailEntregaService.enviarEmailsDeEntregas();
            return ResponseEntity.ok("Envio de e-mails iniciado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar e-mails: " + e.getMessage());
        }
    }

    @GetMapping("/teste")
    public String teste() {
        return "OK";
    }
}
