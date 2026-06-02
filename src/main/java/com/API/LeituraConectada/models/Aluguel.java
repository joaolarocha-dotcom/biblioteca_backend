package com.API.LeituraConectada.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    private LocalDateTime dataAluguel;
    private LocalDateTime dataDevolucao; // Registra o momento exato em que o livro foi devolvido

    @Enumerated(EnumType.STRING)
    private StatusAluguel status;

    // Função que verifica se o aluguel está atrasado
    // Como regra de negócio fictícia: o prazo padrão para devolver o livro é de 7 dias após o aluguel
    public boolean verificarAtraso() {
        if (this.status == StatusAluguel.DEVOLVIDO) {
            return false; // Se já foi devolvido, não está mais em atraso ativo
        }

        LocalDateTime dataLimite = this.dataAluguel.plusDays(7);
        return LocalDateTime.now().isAfter(dataLimite);
    }
}