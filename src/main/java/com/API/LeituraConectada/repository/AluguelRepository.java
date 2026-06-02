package com.API.LeituraConectada.repository;

import com.API.LeituraConectada.models.Aluguel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Integer> {
    // Busca os aluguéis de um usuário específico e ordena do mais novo para o mais antigo
    List<Aluguel> findByUsuarioIdOrderByDataAluguelDesc(int usuarioId);
}