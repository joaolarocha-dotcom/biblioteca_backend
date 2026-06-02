package com.API.LeituraConectada.services;

import com.API.LeituraConectada.dtos.RequestRecomendacaoDTO;
import com.API.LeituraConectada.dtos.ResponseRecomendacaoDTO;
import com.API.LeituraConectada.models.Livro;
import com.API.LeituraConectada.models.Recomendacao;
import com.API.LeituraConectada.models.Usuario;
import com.API.LeituraConectada.repository.LivroRepository;
import com.API.LeituraConectada.repository.RecomendacaoRepository;
import com.API.LeituraConectada.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    @Autowired
    private RecomendacaoRepository recomendacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    // Conversão interna de Entidade para DTO
    private ResponseRecomendacaoDTO convertePraDTO(Recomendacao rec) {
        return new ResponseRecomendacaoDTO(
                rec.getId(),
                rec.getTexto(),
                rec.getDataEnvio(),
                rec.isLida(),
                rec.getRemetente().getNome(),
                rec.getDestinatario().getNome(),
                rec.getLivroRecomendado().getTitulo()
        );
    }

    @Transactional
    public ResponseRecomendacaoDTO sugerirLivro(int usuarioId, RequestRecomendacaoDTO request) {
        Usuario remetente = usuarioRepository.findById(usuarioId).orElse(null);
        Usuario destinatario = usuarioRepository.findById(request.getAmigoId()).orElse(null);
        Livro livro = livroRepository.findById(request.getLivroId()).orElse(null);

        if (remetente == null || destinatario == null || livro == null) {
            return null;
        }

        // Incrementa a popularidade do livro conforme a regra de negócio solicitada
        livro.incrementarPopularidade();
        livroRepository.save(livro);

        // Cria a recomendação (começa como não lida: false)
        Recomendacao recomendacao = new Recomendacao(
                null,
                request.getTexto(),
                LocalDateTime.now(),
                false,
                remetente,
                destinatario,
                livro
        );

        return convertePraDTO(recomendacaoRepository.save(recomendacao));
    }

    @Transactional
    public ResponseRecomendacaoDTO marcarComoLida(int recomendacaoId) {
        Recomendacao rec = recomendacaoRepository.findById(recomendacaoId).orElse(null);
        if (rec == null) return null;

        rec.setLida(true);
        return convertePraDTO(recomendacaoRepository.save(rec));
    }

    public List<ResponseRecomendacaoDTO> listarRecebidasPorUsuario(int usuarioId) {
        return recomendacaoRepository.findByDestinatarioId(usuarioId).stream()
                .map(this::convertePraDTO)
                .collect(Collectors.toList());
    }
}