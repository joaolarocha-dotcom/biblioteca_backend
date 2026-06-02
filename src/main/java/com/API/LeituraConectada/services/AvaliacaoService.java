package com.API.LeituraConectada.services;

import com.API.LeituraConectada.dtos.RequestAvaliacaoDTO;
import com.API.LeituraConectada.dtos.ResponseAvaliacaoDTO;
import com.API.LeituraConectada.models.Avaliacao;
import com.API.LeituraConectada.models.Livro;
import com.API.LeituraConectada.repository.AvaliacaoRepository;
import com.API.LeituraConectada.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public ResponseAvaliacaoDTO avaliarLivro(int livroId, RequestAvaliacaoDTO request) {
        // 1. Valida se a nota enviada está dentro do intervalo de 0 a 5
        if (request.getNota() < 0 || request.getNota() > 5) {
            return null;
        }

        // 2. Busca o livro que receberá a avaliação
        Livro livro = livroRepository.findById(livroId).orElse(null);
        if (livro == null) {
            return null;
        }

        // 3. Cria a entidade da avaliação e vincula ao livro encontrado
        Avaliacao avaliacao = new Avaliacao(null, request.getNota(), request.getComentario(), livro);
        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);

        // 4. Adiciona a nova avaliação na lista do livro e recalcula a média aritmética
        livro.getAvaliacoes().add(avaliacaoSalva);
        livro.atualizarNotaMedia();
        livroRepository.save(livro);

        // 5. Retorna o DTO de resposta preenchido
        return new ResponseAvaliacaoDTO(
                avaliacaoSalva.getId(),
                avaliacaoSalva.getNota(),
                avaliacaoSalva.getComentario(),
                livro.getTitulo()
        );
    }

    @Transactional
    public boolean deletarAvaliacao(int id) {
        // 1. Busca a avaliação que deve ser apagada
        Avaliacao avaliacao = avaliacaoRepository.findById(id).orElse(null);

        if (avaliacao == null) {
            return false; // Retorna false se a avaliação não existir
        }

        // 2. Guarda a referência do livro antes de apagar a avaliação
        Livro livro = avaliacao.getLivro();

        // 3. Remove a avaliação da lista interna do livro e deleta do banco
        livro.getAvaliacoes().remove(avaliacao);
        avaliacaoRepository.delete(avaliacao);

        // 4. Recalcula a nota média do livro sem essa avaliação e salva o livro
        livro.atualizarNotaMedia();
        livroRepository.save(livro);

        return true;
    }

    // Método auxiliar interno para converter a Entidade Avaliação para DTO de Resposta
    private ResponseAvaliacaoDTO convertePraAvaliacaoDTO(Avaliacao avaliacao) {
        return new ResponseAvaliacaoDTO(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getLivro().getTitulo()
        );
    }

    public java.util.List<ResponseAvaliacaoDTO> obterComentariosPorLivro(int livroId) {
        // 1. Busca o livro no banco de dados
        Livro livro = livroRepository.findById(livroId).orElse(null);

        if (livro == null) {
            return null; // Retorna nulo se o livro não existir
        }

        // 2. Pega a lista de avaliações do livro e converte cada uma para DTO
        return livro.getAvaliacoes().stream()
                .map(this::convertePraAvaliacaoDTO)
                .collect(java.util.stream.Collectors.toList());
    }
}