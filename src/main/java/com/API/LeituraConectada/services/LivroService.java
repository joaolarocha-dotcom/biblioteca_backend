package com.API.LeituraConectada.services;

import com.API.LeituraConectada.repository.LivroRepository;
import com.API.LeituraConectada.dtos.CategoriaDTO;
import com.API.LeituraConectada.dtos.RequestLivroDTO;
import com.API.LeituraConectada.dtos.ResponseLivroDTO;
import com.API.LeituraConectada.models.Categoria;
import com.API.LeituraConectada.models.Livro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    // Converte a entidade contendo Enums para o DTO contendo ID e Nome legíveis
    private ResponseLivroDTO convertePraLivroDTO(Livro livro){
        List<CategoriaDTO> categoriasDto = livro.getCategorias().stream()
                .map(cat -> new CategoriaDTO(cat.getId(), cat.getNome()))
                .collect(Collectors.toList());

        return new ResponseLivroDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getQuantidadeDisponivel(),
                categoriasDto
        );
    }

    // cria novo livro com categorias
    public ResponseLivroDTO salvar(RequestLivroDTO request){
        // Mapeia a lista de IDs [2, 7] recebida para os enums [FANTASIA, AVENTURA]
        Set<Categoria> categoriasEnums = request.getCategoriasIds().stream()
                .map(Categoria::buscarPorId)
                .collect(Collectors.toSet());

        Livro livro = new Livro(null, request.getTitulo(), request.getAutor(), request.getQuantidadeDisponivel(), categoriasEnums);
        Livro livroSalvo = repository.save(livro);
        return convertePraLivroDTO(livroSalvo);
    }

    // listar livros
    public List<ResponseLivroDTO> listarLivros(){
        return repository.findAll()
                .stream()
                .map(this::convertePraLivroDTO)
                .collect(Collectors.toList());
    }

    // ler por ID
    public ResponseLivroDTO buscarPorId(int id){
        return repository.findById(id)
                .map(this::convertePraLivroDTO)
                .orElse(null);
    }

    // atualizar dados e categorias
    public ResponseLivroDTO atualizar(int id, RequestLivroDTO request){
        return repository.findById(id).map(livroExistente -> {
            livroExistente.setTitulo(request.getTitulo());
            livroExistente.setAutor(request.getAutor());
            livroExistente.setQuantidadeDisponivel(request.getQuantidadeDisponivel());

            // Atualiza transformando os novos IDs informados em Enums novamente
            Set<Categoria> novasCategorias = request.getCategoriasIds().stream()
                    .map(Categoria::buscarPorId)
                    .collect(Collectors.toSet());
            livroExistente.setCategorias(novasCategorias);

            Livro livroAtualizado = repository.save(livroExistente);
            return convertePraLivroDTO(livroAtualizado);
        }).orElse(null);
    }

    // deletar livros
    public boolean deletar(int id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}