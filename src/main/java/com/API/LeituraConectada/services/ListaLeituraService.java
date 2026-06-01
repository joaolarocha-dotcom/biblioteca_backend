package com.API.LeituraConectada.services;

import com.API.LeituraConectada.dtos.RequestListaDTO;
import com.API.LeituraConectada.dtos.ResponseListaDTO;
import com.API.LeituraConectada.dtos.ResponseLivroDTO;
import com.API.LeituraConectada.dtos.CategoriaDTO;
import com.API.LeituraConectada.models.ListaLeitura;
import com.API.LeituraConectada.models.Livro;
import com.API.LeituraConectada.models.Usuario;
import com.API.LeituraConectada.repository.ListaLeituraRepository;
import com.API.LeituraConectada.repository.LivroRepository;
import com.API.LeituraConectada.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListaLeituraService {

    @Autowired
    private ListaLeituraRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    // Converte de entidade para DTO respeitando seu estilo sem mapper
    private ResponseListaDTO convertePraListaDTO(ListaLeitura lista) {
        List<ResponseLivroDTO> livrosDto = lista.getLivros().stream().map(livro -> {
            List<CategoriaDTO> categoriasDto = livro.getCategorias().stream()
                    .map(cat -> new CategoriaDTO(cat.getId(), cat.getNome()))
                    .collect(Collectors.toList());
            return new ResponseLivroDTO(livro.getId(), livro.getTitulo(), livro.getAutor(), livro.getQuantidadeDisponivel(), categoriasDto);
        }).collect(Collectors.toList());

        String nomeDono = (lista.getDono() != null) ? lista.getDono().getNome() : "Desconhecido";

        return new ResponseListaDTO(
                lista.getId(),
                lista.getTitulo(),
                lista.getPopularidade(),
                nomeDono,
                livrosDto
        );
    }

    // Cria nova lista vazia vinculada a um usuário
    public ResponseListaDTO criarLista(RequestListaDTO request) {
        Usuario dono = usuarioRepository.findById(request.getDonoId()).orElse(null);
        if (dono == null) return null;

        ListaLeitura lista = new ListaLeitura(null, request.getTitulo(), dono, new ArrayList<>(), new java.util.HashSet<>());
        return convertePraListaDTO(repository.save(lista));
    }

    // Busca a lista e incrementa a popularidade caso outro usuário a acesse
    public ResponseListaDTO buscarPorIdComAcesso(int listaId, int usuarioAcessandoId) {
        ListaLeitura lista = repository.findById(listaId).orElse(null);
        if (lista == null) return null;

        lista.registrarAcesso(usuarioAcessandoId); // Atualiza a popularidade caso o id seja de terceiros
        repository.save(lista);

        return convertePraListaDTO(lista);
    }

    // Adiciona um livro à lista
    public ResponseListaDTO adicionarLivro(int listaId, int livroId) {
        ListaLeitura lista = repository.findById(listaId).orElse(null);
        Livro livro = livroRepository.findById(livroId).orElse(null);

        if (lista != null && livro != null && !lista.getLivros().contains(livro)) {
            lista.getLivros().add(livro);
            return convertePraListaDTO(repository.save(lista));
        }
        return null;
    }

    // Remove um livro da lista
    public ResponseListaDTO removerLivro(int listaId, int livroId) {
        ListaLeitura lista = repository.findById(listaId).orElse(null);
        Livro livro = livroRepository.findById(livroId).orElse(null);

        if (lista != null && livro != null) {
            lista.getLivros().remove(livro);
            return convertePraListaDTO(repository.save(lista));
        }
        return null;
    }
}