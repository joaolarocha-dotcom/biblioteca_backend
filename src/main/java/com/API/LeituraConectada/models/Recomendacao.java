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
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String texto;
    private LocalDateTime dataEnvio;
    private boolean lida; // Opção de marcar como lida (true ou false)

    @ManyToOne
    @JoinColumn(name = "remetente_id")
    private Usuario remetente; // Usuário que está sugerindo

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatario; // Usuário amigo que recebe a sugestão

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livroRecomendado;
}