package com.API.LeituraConectada.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestListaDTO {
    private String titulo;
    private Integer donoId; // ID do usuário que está criando a lista
}