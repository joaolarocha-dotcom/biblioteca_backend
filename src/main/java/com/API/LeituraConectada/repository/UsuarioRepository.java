package com.API.LeituraConectada.repository;

import com.API.LeituraConectada.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {
    Optional<Usuario> findByDocumento(String documento);

}
