package com.API.LeituraConectada.models;

public enum StatusAluguel {
    ATIVO,      // O livro está com o usuário e o prazo ainda não expirou ou não foi devolvido
    DEVOLVIDO,  // O livro já foi entregue de volta à biblioteca
    ATRASADO    // O livro passou do prazo de devolução e ainda não foi entregue
}