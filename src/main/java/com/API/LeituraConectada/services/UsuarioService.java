package com.API.LeituraConectada.services;

import com.API.LeituraConectada.Repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.API.LeituraConectada.dto.RequestUsuarioDTO;
import com.API.LeituraConectada.dto.ResponseUsuarioDTO;

import com.API.LeituraConectada.model.Usuario;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    private ResponseUsuarioDTO convertePraUsuarioDTO(Usuario usuario){
        return new ResponseUsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getDocumento(),usuario.getEmail());
    }

    //cria novo usuario
    public ResponseUsuarioDTO salvar(RequestUsuarioDTO request){
        Usuario usuario = new Usuario(null, request.getNome(),request.getDocumento(), request.getEmail());
        Usuario usarioSalvo = repository.save(usuario);
        return convertePraUsuarioDTO(usarioSalvo);
    }

    //listar usuarios

    public List<ResponseUsuarioDTO> listarUsuarios(){
        return repository.findAll()
                .stream()
                .map(this::convertePraUsuarioDTO)
                .collect(Collectors.toList());
    }

    //ler por ID
    public ResponseUsuarioDTO buscarPorId(int id){
        return repository.findById(id)
                .map(this::convertePraUsuarioDTO)
                .orElse(null);
    }

    //atualizar dados
    public ResponseUsuarioDTO atualizar(int id, RequestUsuarioDTO request){
        return repository.findById(id).map(usuarioExistente -> {
            usuarioExistente.setDocumento(request.getDocumento());
            usuarioExistente.setNome(request.getNome());
            usuarioExistente.setEmail(request.getEmail());

            Usuario usuarioAtualizado = repository.save(usuarioExistente);
            return convertePraUsuarioDTO(usuarioAtualizado);

        }).orElse(null); //se o ID n existe, retorna nulo
    }

    //deletar usuarios

    public boolean deletar(int id){

        if( repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }


}
