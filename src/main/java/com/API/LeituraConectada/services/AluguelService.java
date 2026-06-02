package com.API.LeituraConectada.services;

import com.API.LeituraConectada.dtos.ResponseAluguelDTO;
import com.API.LeituraConectada.dtos.ResponseLivroDTO;
import com.API.LeituraConectada.models.Aluguel;
import com.API.LeituraConectada.models.Livro;
import com.API.LeituraConectada.models.StatusAluguel;
import com.API.LeituraConectada.models.Usuario;
import com.API.LeituraConectada.repository.AluguelRepository;
import com.API.LeituraConectada.repository.LivroRepository;
import com.API.LeituraConectada.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AluguelService {

    @Autowired
    private AluguelRepository aluguelRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    // Método auxiliar interno para reaproveitar a conversão de Entidade para DTO
    private ResponseAluguelDTO convertePraAluguelDTO(Aluguel aluguel) {
        return new ResponseAluguelDTO(
                aluguel.getId(),
                aluguel.getUsuario().getNome(),
                aluguel.getLivro().getTitulo(),
                aluguel.getDataAluguel(),
                aluguel.getDataDevolucao(),
                aluguel.getStatus(),
                aluguel.verificarAtraso() // Chama a função booleana criada no model
        );
    }


    @Autowired
    private LivroService livroService;

    @Transactional
    public ResponseAluguelDTO alugarLivro(int usuarioId, int livroId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        Livro livro = livroRepository.findById(livroId).orElse(null);

        if (usuario == null || livro == null) return null;

        try {
            livro.reduzirEstoque();
        } catch (IllegalStateException e) {
            return null; // Sem estoque
        }

        // ✨ NOVA REGRA: Ao ser alugado, o livro também ganha popularidade
        livro.incrementarPopularidade();
        livroRepository.save(livro);

        Aluguel aluguel = new Aluguel(null, usuario, livro, LocalDateTime.now(), null, StatusAluguel.ATIVO);
        return convertePraAluguelDTO(aluguelRepository.save(aluguel));
    }


    public List<ResponseLivroDTO> buscarUltimosAlugados(int usuarioId) {
        List<Aluguel> alugueis = aluguelRepository.findByUsuarioIdOrderByDataAluguelDesc(usuarioId);

        return alugueis.stream()
                .map(Aluguel::getLivro)
                .distinct()
                .map(livro -> livroService.buscarPorId(livro.getId())) // Usa o serviço existente!
                .collect(Collectors.toList());
    }


    @Transactional
    public ResponseAluguelDTO registrarDevolucao(int aluguelId) {
        // 1. Busca o registro do aluguel existente
        Aluguel aluguel = aluguelRepository.findById(aluguelId).orElse(null);

        // Valida se o aluguel existe e se já não foi devolvido anteriormente
        if (aluguel == null || aluguel.getStatus() == StatusAluguel.DEVOLVIDO) {
            return null;
        }

        // 2. Modifica o status para DEVOLVIDO e aplica a data e hora do momento atual
        aluguel.setStatus(StatusAluguel.DEVOLVIDO);
        aluguel.setDataDevolucao(LocalDateTime.now());

        // 3. Devolve o exemplar do livro de volta ao estoque da biblioteca
        Livro livro = aluguel.getLivro();
        livro.reporEstoque();
        livroRepository.save(livro);

        // 4. Salva as alterações do aluguel finalizado no banco de dados
        return convertePraAluguelDTO(aluguelRepository.save(aluguel));
    }


}
