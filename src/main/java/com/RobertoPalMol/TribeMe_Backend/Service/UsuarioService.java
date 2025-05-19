package com.RobertoPalMol.TribeMe_Backend.Service;

import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuarios> getUserByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuarios obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuarios guardar(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

}


