package com.API.LeituraConectada.repository;

import com.API.LeituraConectada.models.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
}