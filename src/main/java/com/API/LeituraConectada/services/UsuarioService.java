package com.API.LeituraConectada.services;

import com.API.LeituraConectada.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.API.LeituraConectada.dtos.RequestUsuarioDTO;
import com.API.LeituraConectada.dtos.ResponseUsuarioDTO;

import com.API.LeituraConectada.models.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    private ResponseUsuarioDTO convertePraUsuarioDTO(Usuario usuario){
        return new ResponseUsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getDocumento(),usuario.getEmail());
    }

    private String formatarDocumento(String documentoPuro) {
        if (documentoPuro == null) return null;
        // Remove qualquer caractere que não seja número por segurança antes de aplicar a máscara
        String apenasNumeros = documentoPuro.replaceAll("\\D", "");
        if (apenasNumeros.length() == 11) {
            return apenasNumeros.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
        return documentoPuro; // Caso não tenha 11 dígitos, retorna o que foi digitado
    }

    //cria novo usuario
    public ResponseUsuarioDTO salvar(RequestUsuarioDTO request){
        // 1. Formata os 11 números recebidos para o padrão com pontos e traço
        String documentoFormatado = formatarDocumento(request.getDocumento());

        // 2. Busca no banco se já existe alguém com esse documento formatado
        Optional<Usuario> usuarioExistente = repository.findByDocumento(documentoFormatado);

        if (usuarioExistente.isPresent()) {
            // Se já existe, não cria outro! Apenas retorna o que já está no banco convertido para DTO
            return convertePraUsuarioDTO(usuarioExistente.get());
        }

        // 3. Se não existe, cria e salva o novo usuário normalmente
        Usuario usuario = new Usuario(null, request.getNome(), documentoFormatado, request.getEmail());
        Usuario usuarioSalvo = repository.save(usuario);
        return convertePraUsuarioDTO(usuarioSalvo);
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
            usuarioExistente.setDocumento(formatarDocumento(request.getDocumento()));
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
