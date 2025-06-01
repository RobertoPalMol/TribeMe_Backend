package com.RobertoPalMol.TribeMe_Backend.DTO;

import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class EventoDTO {
    private Long eventoId;
    private String nombre;
    private String descripcion;
    private LocalTime hora;
    private String lugar;
    private LocalDateTime fechaCreacion;
    private Long tribuId;
    private List<UsuarioDTO> miembrosEvento;



    public EventoDTO(Long eventoId, String nombre, String descripcion, LocalTime hora, String lugar, Long tribuId, List<UsuarioDTO> miembrosEvento) {
        this.eventoId = eventoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hora = hora;
        this.lugar = lugar;
        this.tribuId = tribuId;
        this.miembrosEvento = miembrosEvento;
    }

    //getter y setters

    public Long getEventoId() {
        return eventoId;
    }

    public void setEventoId(Long eventoId) {
        this.eventoId = eventoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getTribuId() {
        return tribuId;
    }

    public void setTribuId(Long tribuId) {
        this.tribuId = tribuId;
    }

    public List<UsuarioDTO> getMiembrosEvento() {
        return miembrosEvento;
    }

    public void setMiembrosEvento(List<UsuarioDTO> miembrosEvento) {
        this.miembrosEvento = miembrosEvento;
    }
}
