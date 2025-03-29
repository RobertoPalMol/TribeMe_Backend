package com.RobertoPalMol.TribeMe_Backend.Service;

import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuarios> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuarios> getUserById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuarios createUser(Usuarios usuarios) {
        return usuarioRepository.save(usuarios);
    }

    public Optional<Usuarios> updateUser(Long id, Usuarios usuariosDetails) {
        Optional<Usuarios> user = usuarioRepository.findById(id);
        if (user.isPresent()) {
            Usuarios updatedUsuarios = user.get();
            updatedUsuarios.setCorreo(usuariosDetails.getCorreo());
            updatedUsuarios.setNombre(usuariosDetails.getNombre());
            updatedUsuarios.setContraseña(usuariosDetails.getContraseña());
            updatedUsuarios.setFechaModificacion(usuariosDetails.getFechaModificacion());
            updatedUsuarios.setImagen(usuariosDetails.getImagen());
            usuarioRepository.save(updatedUsuarios);
            return Optional.of(updatedUsuarios);
        }
        return Optional.empty();
    }

    public boolean deleteUser(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


