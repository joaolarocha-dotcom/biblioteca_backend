package com.API.LeituraConectada.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseRecomendacaoDTO {
    private Integer id;
    private String texto;
    private LocalDateTime dataEnvio;
    private boolean lida;
    private String nomeRemetente;
    private String nomeDestinatario;
    private String tituloLivroRecomendado;
}