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
public class ResponseListaDTO {
    private Integer id;
    private String titulo;
    private int popularidade;
    private String nomeDono;
    private List<ResponseLivroDTO> livros;
}