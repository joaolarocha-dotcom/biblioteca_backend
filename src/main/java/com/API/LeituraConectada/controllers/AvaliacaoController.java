package com.API.LeituraConectada.controllers;

import com.API.LeituraConectada.dtos.RequestAvaliacaoDTO;
import com.API.LeituraConectada.dtos.ResponseAvaliacaoDTO;
import com.API.LeituraConectada.services.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService service;

    @PostMapping("/livro/{livroId}")
    public ResponseEntity<ResponseAvaliacaoDTO> criarAvaliacao(
            @PathVariable int livroId,
            @RequestBody RequestAvaliacaoDTO dados) {

        var novaAvaliacao = service.avaliarLivro(livroId, dados);

        if (novaAvaliacao == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(novaAvaliacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable int id) {
        boolean deletado = service.deletarAvaliacao(id);

        if (!deletado) {
            return ResponseEntity.notFound().build(); // Retorna 404 se o ID não existir
        }

        return ResponseEntity.noContent().build(); // Retorna 204 No Content se der certo
    }
}