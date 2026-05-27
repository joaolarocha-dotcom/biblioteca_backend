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
    LivroService service;

    @GetMapping
    public List<ResponseLivroDTO> listar() {
        return service.obterLivros();
    }

    @PostMapping
    public ResponseEntity<ResponseLivroDTO> gravar(@RequestBody RequestLivroDTO novoLivro) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.salvar(novoLivro));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseLivroDTO> buscarPorId(@PathVariable(name = "id") int id) {
        var livroEncontrado = service.obterLivroPorId(id);
        return ResponseEntity.ok(livroEncontrado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable int id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }


}