package com.API.LeituraConectada.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLivroDTO {
    private String titulo;
    private String autor;
    private int quantidadeDisponivel;
    private List<Integer> categoriasIds; // Recebe uma lista de IDs. Ex: [2, 7]
}