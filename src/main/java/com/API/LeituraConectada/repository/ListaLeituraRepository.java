package com.API.LeituraConectada.repository;

import com.API.LeituraConectada.models.ListaLeitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaLeituraRepository extends JpaRepository<ListaLeitura, Integer> {
}