package com.API.LeituraConectada.Repositories;

import com.API.LeituraConectada.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario, Integer> {

}
