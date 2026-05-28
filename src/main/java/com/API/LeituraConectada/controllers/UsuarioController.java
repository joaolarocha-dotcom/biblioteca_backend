package com.API.LeituraConectada.controllers;

import com.API.LeituraConectada.dto.RequestUsuarioDTO;
import com.API.LeituraConectada.dto.ResponseUsuarioDTO;
import com.API.LeituraConectada.services.UsuarioService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<ResponseUsuarioDTO> listarTodos(){
        return service.listarUsuarios();
    }

    @PostMapping
    public ResponseEntity<ResponseUsuarioDTO> gravar(@RequestBody RequestUsuarioDTO request){
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
    public ResponseEntity<ResponseUsuarioDTO> atualizar(@PathVariable int id,@RequestBody RequestUsuarioDTO request){
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

}
