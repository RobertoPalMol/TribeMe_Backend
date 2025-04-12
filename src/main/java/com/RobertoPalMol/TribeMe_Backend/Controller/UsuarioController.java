package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.DTO.LoginRequest;
import com.RobertoPalMol.TribeMe_Backend.DTO.UsuarioDTO;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import com.RobertoPalMol.TribeMe_Backend.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuarios> getAllUsers() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> getUserById(@PathVariable Long id) {
        Optional<Usuarios> user = usuarioRepository.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    //logica del login
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return usuarioService.getUserByCorreo(loginRequest.getCorreo())
                .map(usuario -> {
                    if (usuario.getContraseña().equals(loginRequest.getContraseña())) {
                        UsuarioDTO dto = new UsuarioDTO(
                                usuario.getUsuarioId(),
                                usuario.getCorreo(),
                                usuario.getNombre(),
                                usuario.getImagen(),
                                usuario.getFechaCreacion()
                        );
                        return ResponseEntity.ok(dto);
                    } else {
                        return ResponseEntity.status(401).body("Contraseña incorrecta");
                    }
                })
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

