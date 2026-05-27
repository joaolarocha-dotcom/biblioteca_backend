package com.API.LeituraConectada.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private int quantidadeDisponivel;

    public Livro(String titulo, String autor, int quantidadeDisponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.quantidadeDisponivel = quantidadeDisponivel;
    };

    public boolean verificaDisponibilidade(){
        return this.quantidadeDisponivel > 0;
    }


}
