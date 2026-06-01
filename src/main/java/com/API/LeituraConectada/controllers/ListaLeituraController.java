package com.API.LeituraConectada.controllers;

import com.API.LeituraConectada.dtos.RequestListaDTO;
import com.API.LeituraConectada.dtos.ResponseListaDTO;
import com.API.LeituraConectada.services.ListaLeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("listas")
public class ListaLeituraController {

    @Autowired
    private ListaLeituraService service;

    // Criar uma lista
    @PostMapping
    public ResponseEntity<ResponseListaDTO> criar(@RequestBody RequestListaDTO request) {
        var novaLista = service.criarLista(request);
        if (novaLista == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLista);
    }

    // Visualizar lista passando o id de quem está visualizando para calcular a popularidade
    @GetMapping("/{id}")
    public ResponseEntity<ResponseListaDTO> obterPorId(@PathVariable int id, @RequestParam int usuarioLogadoId) {
        var lista = service.buscarPorIdComAcesso(id, usuarioLogadoId);
        if (lista == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(lista);
    }

    // Adicionar livro na lista
    @PostMapping("/{listaId}/livros/{livroId}")
    public ResponseEntity<ResponseListaDTO> adicionarLivro(@PathVariable int listaId, @PathVariable int livroId) {
        var listaAtualizada = service.adicionarLivro(listaId, livroId);
        if (listaAtualizada == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(listaAtualizada);
    }

    // Remover livro da lista
    @DeleteMapping("/{listaId}/livros/{livroId}")
    public ResponseEntity<ResponseListaDTO> removerLivro(@PathVariable int listaId, @PathVariable int livroId) {
        var listaAtualizada = service.removerLivro(listaId, livroId);
        if (listaAtualizada == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(listaAtualizada);
    }
}