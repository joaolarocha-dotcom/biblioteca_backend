package com.API.LeituraConectada.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ListaLeitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario dono;

    @ManyToMany
    @JoinTable(
            name = "lista_livros",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id")
    )
    private List<Livro> livros;

    // Guarda os IDs dos usuários que já acessaram esta lista para calcular a popularidade
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "lista_acessos_usuarios", joinColumns = @JoinColumn(name = "lista_id"))
    private Set<Integer> usuariosQueAcessaram = new HashSet<>();

    // A popularidade é a quantidade de usuários diferentes que viram a lista
    public int getPopularidade() {
        return this.usuariosQueAcessaram.size();
    }

    public void registrarAcesso(int usuarioId) {
        // Só conta popularidade se quem está acessando NÃO for o dono da lista
        if (this.dono != null && this.dono.getId() != usuarioId) {
            this.usuariosQueAcessaram.add(usuarioId);
        }
    }
}