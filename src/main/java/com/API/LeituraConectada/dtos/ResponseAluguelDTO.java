package com.API.LeituraConectada.dtos;

import com.API.LeituraConectada.models.StatusAluguel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAluguelDTO {
    private Integer id;
    private String nomeUsuario;
    private String tituloLivro;
    private LocalDateTime dataAluguel;
    private LocalDateTime dataDevolucao;
    private StatusAluguel status;
    private boolean atrasado; // Campo booleano gerado dinamicamente
}