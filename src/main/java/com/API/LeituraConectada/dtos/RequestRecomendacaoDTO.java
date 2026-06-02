package com.API.LeituraConectada.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestRecomendacaoDTO {
    private int amigoId; // ID do usuário que vai receber a indicação
    private int livroId; // ID do livro sugerido
    private String texto; // Mensagem personalizada (ex: "Você vai amar esse livro!")
}