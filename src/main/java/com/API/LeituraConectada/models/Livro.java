package com.API.LeituraConectada.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String autor;
    private int quantidadeDisponivel;

    @ElementCollection(fetch = FetchType.EAGER) // Cria a tabela relacional automaticamente
    @CollectionTable(name = "livro_categorias", joinColumns = @JoinColumn(name = "livro_id"))
    @Enumerated(EnumType.STRING)
    private Set<Categoria> categorias; // Guarda a lista de enums fixos

    public boolean verificaDisponibilidade(){
        return this.quantidadeDisponivel > 0;
    }
}