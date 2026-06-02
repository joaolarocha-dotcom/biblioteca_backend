package com.API.LeituraConectada.repository;

import com.API.LeituraConectada.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Integer> {

    // Busca os livros ordenados pela popularidade de forma decrescente e limita o resultado aos 10 primeiros
    List<Livro> findTop10ByOrderByPopularidadeDesc();
}