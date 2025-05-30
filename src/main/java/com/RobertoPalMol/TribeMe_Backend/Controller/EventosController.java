package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.DTO.EventoDTO;
import com.RobertoPalMol.TribeMe_Backend.Entity.Eventos;
import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.EventosRepository;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribuRepository;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import com.RobertoPalMol.TribeMe_Backend.Service.EventoService;
import com.RobertoPalMol.TribeMe_Backend.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eventos")
public class EventosController {

    private static final Logger log = LoggerFactory.getLogger(EventosController.class);

    @Autowired
    private EventosRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TribuRepository tribuRepository;

    private final EventoService eventoService;
    private final UsuarioService usuarioService;

    public EventosController(EventoService eventoService, UsuarioService usuarioService) {
        this.eventoService = eventoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<EventoDTO>> getAllEventos(Authentication authentication) {
        log.debug("getAllEventos called, principal={}",
                authentication != null ? authentication.getName() : "anonymous");
        List<EventoDTO> eventos = eventoRepository.findAll().stream()
                .map(e -> new EventoDTO(
                        e.getEventoId(),
                        e.getNombre(),
                        e.getDescripcion(),
                        e.getHora(),
                        e.getLugar(),
                        e.getFechaCreacion(),
                        e.getEventoCreador().getUsuarioId().toString(),
                        e.getEventoCreador().getNombre(),
                        e.getTribu().getTribuId()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> getEventoById(@PathVariable Long id, Authentication authentication) {
        Optional<Usuarios> userOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Eventos> eventoOpt = eventoRepository.findById(id);
        if (eventoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Eventos e = eventoOpt.get();
        EventoDTO dto = new EventoDTO(
                e.getEventoId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getHora(),
                e.getLugar(),
                e.getFechaCreacion(),
                e.getEventoCreador().getUsuarioId().toString(),
                e.getEventoCreador().getNombre(),
                e.getTribu().getTribuId()
        );
        return ResponseEntity.ok(dto);
    }


    @PostMapping
    public ResponseEntity<EventoDTO> createEvento(@RequestBody EventoDTO dto, Authentication authentication) {
        Optional<Usuarios> userOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Tribus> tribuOpt = tribuRepository.findById(dto.getTribuId());
        if (tribuOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Usuarios creador = userOpt.get();
        Tribus tribu = tribuOpt.get();

        Eventos evento = new Eventos();
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setHora(dto.getHora());
        evento.setLugar(dto.getLugar());
        evento.setFechaCreacion(LocalDateTime.now());
        evento.setFechaModificacion(LocalDateTime.now());
        evento.setEventoCreador(creador);
        evento.setTribu(tribu);

        eventoRepository.save(evento);

        log.info("Recibido EventoDTO: {}", dto);

        EventoDTO response = new EventoDTO(
                evento.getEventoId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getHora(),
                evento.getLugar(),
                evento.getFechaCreacion(),
                creador.getUsuarioId().toString(),
                creador.getNombre(),
                evento.getTribu().getTribuId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id, Authentication authentication) {
        Optional<Usuarios> userOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!eventoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Optional<Eventos> eventoOpt = eventoRepository.findById(id);
        if (eventoOpt.isPresent()) {
            Eventos evento = eventoOpt.get();
            if (!evento.getEventoCreador().getCorreo().equals(authentication.getName())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }

        eventoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/tribu/{tribuId}")
    public ResponseEntity<List<EventoDTO>> getEventosByTribu(@PathVariable Long tribuId, Authentication authentication) {
        Optional<Usuarios> userOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println("Entrando en getEventosByTribu");
        List<Eventos> eventos = eventoRepository.findByTribu_TribuId(tribuId);
        List<EventoDTO> dtos = eventos.stream()
                .map(e -> new EventoDTO(
                        e.getEventoId(),
                        e.getNombre(),
                        e.getDescripcion(),
                        e.getHora(),
                        e.getLugar(),
                        e.getFechaCreacion(),
                        e.getEventoCreador().getUsuarioId().toString(),
                        e.getEventoCreador().getNombre(),
                        e.getTribu().getTribuId()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{eventoId}/unirse/{usuarioId}")
    public ResponseEntity<?> unirseAEvento(@PathVariable Long eventoId, @PathVariable Long usuarioId) {
        try {
            Usuarios usuario = usuarioService.obtenerPorId(usuarioId);
            Eventos evento = eventoService.obtenerPorId(eventoId);

            if (usuario == null || evento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o evento no encontrados");
            }

            if (evento.getMiembrosEvento().contains(usuario)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya estás inscrito en este evento");
            }

            // Añadir relaciones en ambos sentidos
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

    @DeleteMapping("/{eventoId}/salir/{usuarioId}")
    public ResponseEntity<?> salirDeEvento(@PathVariable Long eventoId, @PathVariable Long usuarioId) {
        try {
            Usuarios usuario = usuarioService.obtenerPorId(usuarioId);
            Eventos evento = eventoService.obtenerPorId(eventoId);

            if (usuario == null || evento == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario o evento no encontrados");
            }

            if (!evento.getMiembrosEvento().contains(usuario)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("No estás inscrito en este evento");
            }

            // Eliminar relaciones en ambos sentidos
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

}

