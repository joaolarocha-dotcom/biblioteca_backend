package com.API.LeituraConectada.controllers;

import com.API.LeituraConectada.dtos.ResponseAluguelDTO;
import com.API.LeituraConectada.services.AluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("alugueis")
public class AluguelController {

    @Autowired
    private AluguelService service;

    // Rota para Alugar (Criar aluguel)
    @PostMapping("/usuario/{usuarioId}/livro/{livroId}")
    public ResponseEntity<ResponseAluguelDTO> realizarAluguel(
            @PathVariable int usuarioId,
            @PathVariable int livroId) {

        var aluguelEfetuado = service.alugarLivro(usuarioId, livroId);
        if (aluguelEfetuado == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(aluguelEfetuado);
    }

    // Rota para Devolver (Registrar devolução)
    @PutMapping("/{id}/devolucao")
    public ResponseEntity<ResponseAluguelDTO> devolverLivro(@PathVariable int id) {
        var devolucaoEfetuada = service.registrarDevolucao(id);

        if (devolucaoEfetuada == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(devolucaoEfetuada);
    }
}