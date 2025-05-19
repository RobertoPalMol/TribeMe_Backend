package com.RobertoPalMol.TribeMe_Backend.DTO;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventoDTO {
    private Long eventoId;
    private String nombre;
    private String descripcion;
    private LocalTime hora;
    private String lugar;
    private LocalDateTime fechaCreacion;
    private String autorId;
    private String autorNombre;

    public EventoDTO(Long eventoId, String nombre, String descripcion, LocalTime hora, String lugar,
                     LocalDateTime fechaCreacion, String autorId, String autorNombre) {
        this.eventoId = eventoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.hora = hora;
        this.lugar = lugar;
        this.fechaCreacion = fechaCreacion;
        this.autorId = autorId;
        this.autorNombre = autorNombre;
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

    public String getAutorId() {
        return autorId;
    }

    public void setAutorId(String autorId) {
        this.autorId = autorId;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }
}
