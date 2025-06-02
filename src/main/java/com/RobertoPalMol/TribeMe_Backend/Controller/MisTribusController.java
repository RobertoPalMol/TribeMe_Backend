package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.DTO.TribuDTO;
import com.RobertoPalMol.TribeMe_Backend.DTO.UsuarioDTO;
import com.RobertoPalMol.TribeMe_Backend.Entity.Categorias;
import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/mis")
public class MisTribusController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public ResponseEntity<?> getMisTribus(Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
        }

        Optional<Usuarios> usuarioOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuarios usuario = usuarioOpt.get();

        List<Tribus> misTribus = usuario.getTribus();

        List<TribuDTO> dtos = misTribus.stream()
                .map(tribu -> {
                    List<String> nombresCat = tribu.getCategorias().stream()
                            .map(Categorias::getNombre)
                            .collect(Collectors.toList());

                    List<UsuarioDTO> miembrosDto = tribu.getMiembrosTribu().stream()
                            .map(u -> new UsuarioDTO(
                                    u.getUsuarioId(),
                                    u.getCorreo(),
                                    u.getNombre(),
                                    u.getImagen(),
                                    u.getFechaCreacion()
                            ))
                            .collect(Collectors.toList());

                    return new TribuDTO(
                            tribu.getTribuId(),
                            tribu.getNombre(),
                            tribu.getDescripcion(),
                            tribu.getImagen(),
                            nombresCat,
                            tribu.getUsuariosMaximos(),
                            tribu.isTribuPrivada(),
                            tribu.getFechaCreacion(),
                            tribu.getTribuCreador().getUsuarioId().toString(),
                            tribu.getTribuCreador().getNombre(),
                            miembrosDto,
                            tribu.getUbicacion(),
                            tribu.isCrearEventos()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
