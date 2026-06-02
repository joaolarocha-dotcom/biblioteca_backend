package com.API.LeituraConectada.models;

import lombok.Getter;

@Getter
public enum Categoria {
    ROMANCE(1, "Romance"),
    FANTASIA(2, "Fantasia"),
    FICCAO_CIENTIFICA(3, "Ficção científica"),
    SUSPENSE(4, "Suspense"),
    TERROR(5, "Terror"),
    AVENTURA(7, "Aventura"),
    BIOGRAFIA(8, "Biografia");

    private final int id;
    private final String nome;

    Categoria(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Método para encontrar o Enum correto enviando apenas o ID número (ex: 2 -> FANTASIA)
    public static Categoria buscarPorId(int id) {
        for (Categoria cat : values()) {
            if (cat.getId() == id) {
                return cat;
            }
        }
        throw new IllegalArgumentException("Categoria com ID " + id + " não existe.");
    }
}