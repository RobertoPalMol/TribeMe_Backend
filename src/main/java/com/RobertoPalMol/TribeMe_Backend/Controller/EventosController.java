package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.DTO.EventoDTO;
import com.RobertoPalMol.TribeMe_Backend.Entity.Eventos;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.EventosRepository;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
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
                        e.getEventoCreador().getNombre()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> getEventoById(@PathVariable Long id) {
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
                e.getEventoCreador().getNombre()
        );
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EventoDTO> createEvento(@RequestBody EventoDTO dto, Authentication authentication) {
        Optional<Usuarios> userOpt = usuarioRepository.findByCorreo(authentication.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Usuarios creador = userOpt.get();

        Eventos evento = new Eventos();
        evento.setNombre(dto.getNombre());
        evento.setDescripcion(dto.getDescripcion());
        evento.setHora(dto.getHora());
        evento.setLugar(dto.getLugar());
        evento.setFechaCreacion(LocalDateTime.now());
        evento.setFechaModificacion(LocalDateTime.now());
        evento.setEventoCreador(creador);

        eventoRepository.save(evento);

        EventoDTO response = new EventoDTO(
                evento.getEventoId(),
                evento.getNombre(),
                evento.getDescripcion(),
                evento.getHora(),
                evento.getLugar(),
                evento.getFechaCreacion(),
                creador.getUsuarioId().toString(),
                creador.getNombre()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        if (!eventoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        eventoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

