package com.API.LeituraConectada.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String titulo;
    private String autor;
    private int quantidadeDisponivel;

    @ElementCollection(fetch = FetchType.EAGER) // Cria a tabela relacional automaticamente
    @CollectionTable(name = "livro_categorias", joinColumns = @JoinColumn(name = "livro_id"))
    @Enumerated(EnumType.STRING)
    private Set<Categoria> categorias; // Guarda a lista de enums fixos

    public boolean verificaDisponibilidade(){
        return this.quantidadeDisponivel > 0;
    }

    public void reduzirEstoque() {
        if (this.quantidadeDisponivel <= 0) {
            throw new IllegalStateException("Não há exemplares disponíveis deste livro para aluguel.");
        }
        this.quantidadeDisponivel -= 1;
    }

    public void reporEstoque() {
        this.quantidadeDisponivel += 1;
    }

    // Adicione estes atributos dentro da classe Livro
    private double notaMedia;



    @OneToMany(mappedBy = "livro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Avaliacao> avaliacoes = new ArrayList<>();

    // Método para recalcular a nota média do livro
    public void atualizarNotaMedia() {
        if (this.avaliacoes == null || this.avaliacoes.isEmpty()) {
            this.notaMedia = 0.0;
            return;
        }

        double soma = 0;
        for (Avaliacao avaliacao : this.avaliacoes) {
            soma += avaliacao.getNota();
        }

        this.notaMedia = soma / this.avaliacoes.size();
    }

    private int popularidade;

    public void incrementarPopularidade() {
        this.popularidade += 1;
    }
}