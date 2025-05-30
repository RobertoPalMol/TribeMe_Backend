package com.RobertoPalMol.TribeMe_Backend.Service;

import com.RobertoPalMol.TribeMe_Backend.Entity.Eventos;
import com.RobertoPalMol.TribeMe_Backend.Repository.EventosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventoService {

    private final EventosRepository eventosRepository;

    @Autowired
    public EventoService(EventosRepository eventosRepository) {
        this.eventosRepository = eventosRepository;
    }

    public Eventos obtenerPorId(Long id) {
        return eventosRepository.findById(id).orElse(null);
    }

    public Eventos guardar(Eventos evento) {
        return eventosRepository.save(evento);
    }
}

