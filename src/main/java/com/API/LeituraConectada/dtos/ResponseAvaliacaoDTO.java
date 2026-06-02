package com.API.LeituraConectada.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAvaliacaoDTO {
    private Integer id;
    private int nota;
    private String comentario;
    private String tituloLivro;
}