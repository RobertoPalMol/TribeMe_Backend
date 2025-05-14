package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.DTO.TribuDTO;
import com.RobertoPalMol.TribeMe_Backend.Entity.Categorias;
import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.CategoriaRepository;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribuRepository;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tribus")
public class TribusController {

    private static final Logger log = LoggerFactory.getLogger(TribusController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TribuRepository tribuRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<TribuDTO>> getAllTribus(Authentication authentication) {
        log.debug("getAllTribus called, principal={}", authentication != null ? authentication.getName() : "anonymous");
        List<TribuDTO> dtos = tribuRepository.findAll().stream()
                .map(tribu -> new TribuDTO(
                        tribu.getTribuId(),
                        tribu.getNombre(),
                        tribu.getDescripcion(),
                        tribu.getImagen(),
                        tribu.getCategorias().stream().map(Categorias::getNombre).collect(Collectors.toList()),
                        tribu.getUsuariosMaximos(),
                        tribu.isTribuPrivada(),
                        tribu.getFechaCreacion(),
                        tribu.getTribuCreador().getUsuarioId().toString(),
                        tribu.getTribuCreador().getNombre()
                ))
                .collect(Collectors.toList());
        log.debug("Found {} tribes", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TribuDTO> getTribuById(@PathVariable Long id, Authentication authentication) {
        log.debug("getTribuById called for id={}, principal={}", id,
                authentication != null ? authentication.getName() : "anonymous");
        return tribuRepository.findById(id)
                .map(tribu -> {
                    TribuDTO dto = new TribuDTO(
                            tribu.getTribuId(),
                            tribu.getNombre(),
                            tribu.getDescripcion(),
                            tribu.getImagen(),
                            tribu.getCategorias().stream().map(Categorias::getNombre).collect(Collectors.toList()),
                            tribu.getUsuariosMaximos(),
                            tribu.isTribuPrivada(),
                            tribu.getFechaCreacion(),
                            tribu.getTribuCreador().getUsuarioId().toString(),
                            tribu.getTribuCreador().getNombre()
                    );
                    return ResponseEntity.ok(dto);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TribuDTO> createTribu(
            @RequestBody TribuDTO tribuDTO,
            Authentication authentication) {

        log.debug("createTribu called by principal={} with DTO={} ", authentication.getName(), tribuDTO);

        // Obtener usuario autenticado por correo
        Optional<Usuarios> autorOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (autorOpt.isEmpty()) {
            log.warn("Usuario no encontrado para principal={}", authentication.getName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Usuarios autor = autorOpt.get();

        // Validar categorías
        List<Categorias> categorias = categoriaRepository.findByNombreIn(tribuDTO.getCategorias());
        if (categorias.size() != tribuDTO.getCategorias().size()) {
            log.warn("Categorías inválidas: {}", tribuDTO.getCategorias());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Construir y guardar nueva tribu
        Tribus nueva = new Tribus();
        nueva.setNombre(tribuDTO.getNombre());
        nueva.setDescripcion(tribuDTO.getDescripcion());
        nueva.setImagen(
                (tribuDTO.getImagenUrl() == null || tribuDTO.getImagenUrl().isEmpty())
                        ? null : tribuDTO.getImagenUrl()
        );
        nueva.setUsuariosMaximos(tribuDTO.getNumeroMaximoMiembros());
        nueva.setTribuPrivada(tribuDTO.esPrivada());
        nueva.setFechaCreacion(LocalDateTime.now());
        nueva.setFechaModificacion(LocalDateTime.now());
        nueva.setTribuCreador(autor);
        nueva.setCategorias(categorias);

        if (nueva.getMiembrosTribu() == null) {
            nueva.setMiembrosTribu(new java.util.ArrayList<>());
        }
        nueva.getMiembrosTribu().add(autor);

        if (autor.getTribus() == null) {
            autor.setTribus(new java.util.ArrayList<>());
        }
        autor.getTribus().add(nueva);

        Tribus guardada = tribuRepository.save(nueva);
        log.debug("Tribu creada: id={} por usuarioId={}", guardada.getTribuId(), autor.getUsuarioId());

        // Convertir a DTO de respuesta
        TribuDTO resp = new TribuDTO(
                guardada.getTribuId(),
                guardada.getNombre(),
                guardada.getDescripcion(),
                guardada.getImagen(),
                guardada.getCategorias().stream().map(Categorias::getNombre).collect(Collectors.toList()),
                guardada.getUsuariosMaximos(),
                guardada.isTribuPrivada(),
                guardada.getFechaCreacion(),
                autor.getUsuarioId().toString(),
                autor.getNombre()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTribu(@PathVariable Long id, Authentication authentication) {
        log.debug("deleteTribu called for id={}, principal={}", id,
                authentication != null ? authentication.getName() : "anonymous");
        if (tribuRepository.existsById(id)) {
            tribuRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
