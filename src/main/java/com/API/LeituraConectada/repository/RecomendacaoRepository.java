package com.API.LeituraConectada.repository;

import com.API.LeituraConectada.models.Recomendacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Integer> {
    // Busca todas as recomendações recebidas por um usuário específico
    List<Recomendacao> findByDestinatarioId(int destinatarioId);
}