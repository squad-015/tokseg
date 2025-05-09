package com.squad15.armariointeligente.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "condominios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Condominio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 255)
    private String endereco;

    @Column(nullable = false)
    private String telefone;

    @OneToMany(mappedBy = "condominio")
    @JsonIgnore
    private List<Armario> armarios;
}
