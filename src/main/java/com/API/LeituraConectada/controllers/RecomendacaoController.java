package com.API.LeituraConectada.controllers;

import com.API.LeituraConectada.dtos.RequestRecomendacaoDTO;
import com.API.LeituraConectada.dtos.ResponseRecomendacaoDTO;
import com.API.LeituraConectada.services.RecomendacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("recomendacoes")
@Tag(name = "Recomendações", description = "Endpoints para gerenciamento de recomendações de livros")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService service;

    // Enviar uma recomendação de livro para um amigo
    @PostMapping("/usuario/{usuarioId}/sugerir")
    public ResponseEntity<ResponseRecomendacaoDTO> sugerir(
            @PathVariable int usuarioId,
            @RequestBody RequestRecomendacaoDTO dados) {

        var novaSugestao = service.sugerirLivro(usuarioId, dados);
        if (novaSugestao == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSugestao);
    }

    // Obter todas as recomendações recebidas por um usuário específico
    @GetMapping("/recebidas/usuario/{usuarioId}")
    public ResponseEntity<List<ResponseRecomendacaoDTO>> listarRecebidas(@PathVariable int usuarioId) {
        return ResponseEntity.ok(service.listarRecebidasPorUsuario(usuarioId));
    }

    // Marcar uma recomendação específica como lida
    @PutMapping("/{id}/ler")
    public ResponseEntity<ResponseRecomendacaoDTO> ler(@PathVariable int id) {
        var atualizada = service.marcarComoLida(id);
        if (atualizada == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(atualizada);
    }
}