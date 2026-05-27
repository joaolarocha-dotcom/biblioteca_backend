package com.API.LeituraConectada.repository;

import com.API.LeituraConectada.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long>{
}