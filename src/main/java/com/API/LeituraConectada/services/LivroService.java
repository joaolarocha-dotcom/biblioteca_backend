package com.API.LeituraConectada.services;

import com.API.LeituraConectada.dtos.RequestLivroDTO;
import com.API.LeituraConectada.dtos.ResponseLivroDTO;
import com.API.LeituraConectada.repository.LivroRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LivroService {
    private final LivroRepository repository;
    private final LivroMapper mapper;

    public List<ResponseLivroDTO> obterLivros() {
        return repository.findAll().stream()
                .map(mapper::mapResponseFromLivro)
                .toList();
    }

    public ResponseLivroDTO salvar(RequestLivroDTO nova) {
        EntLivro livro = mapper.toEntLivro(nova);
        var livroSalva = repository.save(livro);

        return mapper.mapResponseFromLivro(livroSalva);
    }

    public ResponseLivroDTO obterLivroPorId(Long id) {
        var livroEncontrada = repository.findById(id)
                .orElseThrow(() -> new BadRequest("Livro não encontrada"));

        return mapper.mapResponseFromLivro(livroEncontrada);
    }

    public void remover(Long id) {
        var livro = repository.findById(id)
                .orElseThrow(() -> new BadRequest("Livro não encontrado"));

        repository.delete(livro);
    }
}
