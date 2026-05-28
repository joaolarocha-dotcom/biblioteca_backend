package com.API.LeituraConectada.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUsuarioDTO {
    private String nome;
    private String documento;
    private String email;
}
