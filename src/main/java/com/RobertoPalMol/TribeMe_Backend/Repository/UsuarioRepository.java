package com.RobertoPalMol.TribeMe_Backend.Repository;

import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    //Logica para filtrar por correo
    Optional<Usuarios> findByCorreo(String correo);
}
