package com.squad15.armariointeligente.model;

import jakarta.persistence.*;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "armarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Armario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String localizacao;

    @ManyToOne
    @JoinColumn(name = "id_condominio", nullable = false)
    private Condominio condominio;

    @OneToMany(mappedBy = "armario")
    private List<Compartimento> compartimentos;
}
