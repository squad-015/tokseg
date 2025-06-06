package com.squad15.armariointeligente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class EmailEntregaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailsDeEntregas() {
        String sql = "SELECT e.id, e.codigo_rastreio, e.data_entrega, u.nome, u.email " +
                     "FROM entregas e " +
                     "JOIN usuarios u ON e.id_usuario = u.id";

        List<Map<String, Object>> entregas = jdbcTemplate.queryForList(sql);

        for (Map<String, Object> entrega : entregas) {
            String nome = (String) entrega.get("nome");
            String email = (String) entrega.get("email");
            String codigo = (String) entrega.get("codigo_rastreio");
            Timestamp dataEntrega = (Timestamp) entrega.get("data_entrega");

            String assunto = "Nova entrega no seu armário inteligente";
            String texto = String.format("Olá %s,\n\nSua entrega com código %s foi registrada em %s.\n\nObrigado!",
                                         nome, codigo, dataEntrega.toLocalDateTime().toLocalDate());

            enviarEmail(email, assunto, texto);
        }
    }

    private void enviarEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setTo(to);
            mensagem.setSubject(subject);
            mensagem.setText(text);

            mailSender.send(mensagem);
            System.out.println("Email enviado para " + to);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email para " + to + ": " + e.getMessage());
        }
    }
}
