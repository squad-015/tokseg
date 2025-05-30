package com.squad15.armariointeligente.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squad15.armariointeligente.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ErrorResponse errorResponse = new ErrorResponse(
                new Date(),
                HttpStatus.FORBIDDEN.value(),
                "Acesso negado",
                "Você não tem permissão para acessar este recurso",
                request.getRequestURI()
        );

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}

