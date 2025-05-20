package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.DTO.TribuDTO;
import com.RobertoPalMol.TribeMe_Backend.DTO.UsuarioDTO;
import com.RobertoPalMol.TribeMe_Backend.Entity.Categorias;
import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.CategoriaRepository;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribuRepository;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import com.RobertoPalMol.TribeMe_Backend.Service.TribuService;
import com.RobertoPalMol.TribeMe_Backend.Service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private UsuarioService usuarioService;

    @Autowired
    private TribuService tribuService;

    @Autowired
    private TribuRepository tribuRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<TribuDTO>> getAllTribus(Authentication authentication) {
        log.debug("getAllTribus called, principal={}", authentication != null ? authentication.getName() : "anonymous");

        List<TribuDTO> dtos = tribuRepository.findAll().stream()
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
                            tribu.getUbicacion()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TribuDTO> getTribuById(
            @PathVariable Long id,
            Authentication authentication) {

        Optional<Tribus> opt = tribuRepository.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Tribus tribu = opt.get();

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

        TribuDTO dto = new TribuDTO(
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
                tribu.getUbicacion()
        );

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TribuDTO> createTribu(
            @RequestBody TribuDTO tribuDTO,
            Authentication authentication) {

        Optional<Usuarios> autorOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (autorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Usuarios autor = autorOpt.get();

        List<Categorias> categoriasEnt = categoriaRepository.findByNombreIn(tribuDTO.getCategorias());
        if (categoriasEnt.size() != tribuDTO.getCategorias().size()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Tribus nueva = new Tribus();
        nueva.setNombre(tribuDTO.getNombre());
        nueva.setDescripcion(tribuDTO.getDescripcion());
        nueva.setImagen(tribuDTO.getImagenUrl());
        nueva.setUsuariosMaximos(tribuDTO.getNumeroMaximoMiembros());
        nueva.setTribuPrivada(tribuDTO.isEsPrivada());
        nueva.setFechaCreacion(LocalDateTime.now());
        nueva.setFechaModificacion(LocalDateTime.now());
        nueva.setTribuCreador(autor);
        nueva.setCategorias(categoriasEnt);
        nueva.setUbicacion(tribuDTO.getUbicacion());
        nueva.setMiembrosTribu(new ArrayList<>());
        nueva.getMiembrosTribu().add(autor);

        if (autor.getTribus() == null) {
            autor.setTribus(new ArrayList<>());
        }
        autor.getTribus().add(nueva);

        Tribus guardada = tribuRepository.save(nueva);

        List<String> nombresCat = categoriasEnt.stream()
                .map(Categorias::getNombre)
                .collect(Collectors.toList());

        List<UsuarioDTO> miembrosDto = guardada.getMiembrosTribu().stream()
                .map(u -> new UsuarioDTO(
                        u.getUsuarioId(),
                        u.getCorreo(),
                        u.getNombre(),
                        u.getImagen(),
                        u.getFechaCreacion()
                ))
                .collect(Collectors.toList());

        TribuDTO resp = new TribuDTO(
                guardada.getTribuId(),
                guardada.getNombre(),
                guardada.getDescripcion(),
                guardada.getImagen(),
                nombresCat,
                guardada.getUsuariosMaximos(),
                guardada.isTribuPrivada(),
                guardada.getFechaCreacion(),
                autor.getUsuarioId().toString(),
                autor.getNombre(),
                miembrosDto,
                guardada.getUbicacion()

        );

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("/{tribuId}/unirse/{usuarioId}")
    public ResponseEntity<?> unirseATribu(@PathVariable Long tribuId, @PathVariable Long usuarioId) {
        try {
            Usuarios usuario = usuarioService.obtenerPorId(usuarioId);
            Tribus tribu = tribuService.obtenerPorId(tribuId);

            if (usuario == null || tribu == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o tribu no encontrados");
            }

            if (tribu.getMiembrosTribu().contains(usuario)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya perteneces a esta tribu");
            }

            // Añadir relaciones en ambos sentidos
            tribu.getMiembrosTribu().add(usuario);
            usuario.getTribus().add(tribu);

            usuarioService.guardar(usuario);
            tribuService.guardar(tribu);

            return ResponseEntity.ok("Te has unido a la tribu correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al unirse a la tribu: " + e.getMessage());
        }
    }


    @DeleteMapping("/{tribuId}/salir/{usuarioId}")
    public ResponseEntity<?> salirDeTribu(@PathVariable Long tribuId, @PathVariable Long usuarioId) {
        try {
            Usuarios usuario = usuarioService.obtenerPorId(usuarioId);
            Tribus tribu = tribuService.obtenerPorId(tribuId);

            if (usuario == null || tribu == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o tribu no encontrados");
            }

            if (!tribu.getMiembrosTribu().contains(usuario)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No perteneces a esta tribu");
            }

            // Eliminar relaciones en ambos sentidos
            tribu.getMiembrosTribu().remove(usuario);
            usuario.getTribus().remove(tribu);

            usuarioService.guardar(usuario);
            tribuService.guardar(tribu);

            return ResponseEntity.ok("Has salido de la tribu correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al salir de la tribu: " + e.getMessage());
        }
    }


    @GetMapping("/filtrar")
    public ResponseEntity<List<TribuDTO>> buscarTribus(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String ubicacion,
            @RequestParam(required = false) List<String> categorias) {

        List<Tribus> tribusFiltradas = tribuRepository.buscarTribusFiltradas(nombre, ubicacion, categorias);

        List<TribuDTO> dtos = tribusFiltradas.stream()
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
                            tribu.getUbicacion()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTribu(
            @PathVariable Long id,
            Authentication authentication) {
        if (!tribuRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        tribuRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
