package com.API.LeituraConectada.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUsuarioDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve estar em um formato válido.")
    private String email;

    @NotBlank(message = "O documento é obrigatório.")
    @Schema(example = "00000000000", description = "Digite apenas os 11 números do CPF")
    @Pattern(regexp = "^\\d{11}$", message = "O documento deve conter exatamente 11 números.")
    private String documento;
}