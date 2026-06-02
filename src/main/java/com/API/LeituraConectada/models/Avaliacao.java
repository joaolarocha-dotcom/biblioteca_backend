package com.API.LeituraConectada.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int nota; // Valor de 0 a 5
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;
}