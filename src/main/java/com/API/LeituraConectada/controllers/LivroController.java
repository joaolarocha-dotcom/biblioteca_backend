package com.API.LeituraConectada.controllers;

import com.API.LeituraConectada.dtos.RequestLivroDTO;
import com.API.LeituraConectada.dtos.ResponseLivroDTO;
import com.API.LeituraConectada.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("livros")
public class LivroController {

    @Autowired
    private LivroService service;

    @GetMapping
    public List<ResponseLivroDTO> listar() {
        return service.listarLivros();
    }

    @PostMapping
    public ResponseEntity<ResponseLivroDTO> gravar(@RequestBody RequestLivroDTO novoLivro) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(novoLivro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseLivroDTO> buscarPorId(@PathVariable Integer id) { // ✨ Alterado para Long
        var livroEncontrado = service.buscarPorId(id);
        return ResponseEntity.ok(livroEncontrado);
    }

    // ✨ Adicionado PutMapping (Atualizar) igual você tem nos Usuários
    @PutMapping("/{id}")
    public ResponseEntity<ResponseLivroDTO> atualizar(@PathVariable int id, @RequestBody RequestLivroDTO dadosAtualizados) {
        var livroAtualizado = service.atualizar(id, dadosAtualizados);
        return ResponseEntity.ok(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) { // ✨ Alterado para Long e Void
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Adicione esta injeção no topo do seu LivroController junto com as outras
    @Autowired
    private com.API.LeituraConectada.services.AvaliacaoService avaliacaoService;

    // Novo endpoint para obter os comentários de um livro específico
    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<com.API.LeituraConectada.dtos.ResponseAvaliacaoDTO>> obterComentarios(@PathVariable int id) {
        var comentarios = avaliacaoService.obterComentariosPorLivro(id);

        if (comentarios == null) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o livro não existir
        }

        return ResponseEntity.ok(comentarios); // Retorna 200 com a lista de avaliações
    }

    // Endpoint para listar o ranking dos 10 livros mais populares do sistema
    @GetMapping("/populares")
    public ResponseEntity<List<ResponseLivroDTO>> obterOsDezMaisPopulares() {
        List<ResponseLivroDTO> top10 = service.buscarOsDezMaisPopulares();
        return ResponseEntity.ok(top10);
    }
}