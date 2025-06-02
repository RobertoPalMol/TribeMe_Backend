package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.DTO.EventoDTO;
import com.RobertoPalMol.TribeMe_Backend.DTO.UsuarioDTO;
import com.RobertoPalMol.TribeMe_Backend.Entity.Eventos;
import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.EventosRepository;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribuRepository;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import com.RobertoPalMol.TribeMe_Backend.Service.EventoService;
import com.RobertoPalMol.TribeMe_Backend.Service.UsuarioService;
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
@RequestMapping("/api/eventos")
public class EventosController {

    @Autowired
    private EventosRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TribuRepository tribuRepository;


    // Obtener todos los eventos con mapeo a DTO individual
    @GetMapping
    public ResponseEntity<List<EventoDTO>> getAllEventos() {
        List<EventoDTO> dtos = eventoRepository.findAll().stream()
                .map(evento -> mapEventoToDTO(evento))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // Crear un evento nuevo
    @PostMapping
    public ResponseEntity<?> createEvento(@RequestBody EventoDTO eventoDTO, Authentication authentication) {
        Optional<Usuarios> optUser = usuarioRepository.findByCorreo(authentication.getName());
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Tribus> tribuOpt = tribuRepository.findById(eventoDTO.getTribuId());
        if (tribuOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tribu no encontrada");
        }


        Usuarios creador = optUser.get();

        Eventos evento = new Eventos();
        evento.setNombre(eventoDTO.getNombre());
        evento.setDescripcion(eventoDTO.getDescripcion());
        evento.setHora(eventoDTO.getHora());
        evento.setLugar(eventoDTO.getLugar());
        evento.setFechaCreacion(LocalDateTime.now());
        evento.setFechaModificacion(LocalDateTime.now());
        evento.setEventoCreador(creador);
        evento.setTribu(tribuOpt.get());
        evento.setMiembrosEvento(new ArrayList<>());
        evento.getMiembrosEvento().add(creador);

        Eventos guardado = eventoRepository.save(evento);

        EventoDTO resp = mapEventoToDTO(guardado);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    // Unirse a un evento
    @PostMapping("/{eventoId}/unirse/{usuarioId}")
    public ResponseEntity<?> unirseAEvento(@PathVariable Long eventoId, @PathVariable Long usuarioId) {
        try {
            Usuarios usuario = usuarioService.obtenerPorId(usuarioId);
            Eventos evento = eventoService.obtenerPorId(eventoId);

            if (usuario == null || evento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o evento no encontrados");
            }

            if (evento.getMiembrosEvento().contains(usuario)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya estás en este evento");
            }

            evento.getMiembrosEvento().add(usuario);
            usuario.getEventos().add(evento);

            usuarioService.guardar(usuario);
            eventoService.guardar(evento);

            return ResponseEntity.ok("Te has unido al evento correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al unirse al evento: " + e.getMessage());
        }
    }

    @GetMapping("/tribu/{tribuId}")
    public ResponseEntity<List<EventoDTO>> getEventosByTribu(@PathVariable Long tribuId, Authentication authentication) {
        Optional<Usuarios> userOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (userOpt.isEmpty()) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<EventoDTO> dtos = eventoRepository.findByTribu_TribuId(tribuId).stream()
                .map(this::mapEventoToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    // Salir de un evento
    @DeleteMapping("/{eventoId}/salir/{usuarioId}")
    public ResponseEntity<?> salirDeEvento(@PathVariable Long eventoId, @PathVariable Long usuarioId) {
        try {
            Usuarios usuario = usuarioService.obtenerPorId(usuarioId);
            Eventos evento = eventoService.obtenerPorId(eventoId);

            if (usuario == null || evento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o evento no encontrados");
            }

            if (!evento.getMiembrosEvento().contains(usuario)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No perteneces a este evento");
            }

            evento.getMiembrosEvento().remove(usuario);
            usuario.getEventos().remove(evento);

            usuarioService.guardar(usuario);
            eventoService.guardar(evento);

            return ResponseEntity.ok("Has salido del evento correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al salir del evento: " + e.getMessage());
        }
    }

    // Actualizar evento (solo creador)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEvento(@PathVariable Long id, @RequestBody EventoDTO updatedEvento, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Usuarios> usuarioOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuarios usuario = usuarioOpt.get();

        Optional<Eventos> optEvento = eventoRepository.findById(id);
        if (optEvento.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Eventos evento = optEvento.get();

        if (!evento.getEventoCreador().getUsuarioId().equals(usuario.getUsuarioId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos para editar este evento");
        }

        evento.setNombre(updatedEvento.getNombre());
        evento.setDescripcion(updatedEvento.getDescripcion());
        evento.setHora(updatedEvento.getHora());
        evento.setLugar(updatedEvento.getLugar());
        evento.setFechaModificacion(LocalDateTime.now());

        eventoRepository.save(evento);

        return ResponseEntity.ok("Evento actualizado correctamente");
    }

    // Eliminar evento (solo creador)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvento(@PathVariable Long id, Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Usuarios> usuarioOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Eventos> eventoOpt = eventoRepository.findById(id);
        if (eventoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Eventos evento = eventoOpt.get();
        Usuarios usuario = usuarioOpt.get();

        if (!evento.getEventoCreador().getUsuarioId().equals(usuario.getUsuarioId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permisos para eliminar este evento");
        }

        // Eliminar la relación en cada usuario miembro
        for (Usuarios miembro : evento.getMiembrosEvento()) {
            miembro.getEventos().remove(evento);
            usuarioRepository.save(miembro);
        }

        evento.getMiembrosEvento().clear();
        eventoRepository.save(evento);

        eventoRepository.delete(evento);

        return ResponseEntity.noContent().build();
    }

    // Método privado para mapear un Evento a EventoDTO individualmente
    private EventoDTO mapEventoToDTO(Eventos evento) {
        List<UsuarioDTO> miembrosDto = evento.getMiembrosEvento().stream()
                .map(u -> new UsuarioDTO(
                        u.getUsuarioId(),
                        u.getCorreo(),
                        u.getNombre(),
                        u.getImagen(),
                        u.getFechaCreacion()
                ))
                .collect(Collectors.toList());

        return new EventoDTO(
                evento.getEventoId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getHora(),
                evento.getLugar(),
                evento.getTribu().getTribuId(),
                miembrosDto
        );
    }
}
