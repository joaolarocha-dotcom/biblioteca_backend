package com.API.LeituraConectada.controllers;

import com.API.LeituraConectada.dtos.RequestUsuarioDTO;
import com.API.LeituraConectada.dtos.ResponseUsuarioDTO;
import com.API.LeituraConectada.services.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<ResponseUsuarioDTO> listarTodos(){
        return service.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<ResponseUsuarioDTO> gravar(@Valid @RequestBody RequestUsuarioDTO request){
        var dto = service.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> encontrarContaEspecifica(@PathVariable int id){
        var dto = service.buscarPorId(id);
        if (dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUsuarioDTO> atualizar(@PathVariable int id,@Valid @RequestBody RequestUsuarioDTO request){
        var dto = service.atualizar(id, request);
        if (dto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable int id){
        boolean deletado = service.deletar(id);
        if (!deletado){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // Adicione esta injeção no topo do seu UsuarioController
    @Autowired
    private com.API.LeituraConectada.services.AluguelService aluguelService;

    // Endpoint para buscar o histórico de livros alugados do usuário
    @GetMapping("/{id}/ultimos-livros")
    public ResponseEntity<List<com.API.LeituraConectada.dtos.ResponseLivroDTO>> obterUltimosLivrosAlugados(@PathVariable int id) {
        var livros = aluguelService.buscarUltimosAlugados(id);

        // Retorna a lista (pode retornar uma lista vazia de 200 OK se ele nunca tiver alugado nada)
        return ResponseEntity.ok(livros);
    }

}
