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
public class ResponseLivroDTO {
    private Integer id;
    private String titulo;
    private String autor;
    private int quantidadeDisponivel;
    private List<CategoriaDTO> categorias;
    private double notaMedia; }