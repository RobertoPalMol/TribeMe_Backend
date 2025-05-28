package com.RobertoPalMol.TribeMe_Backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "Eventos")
public class Eventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eventoId")
    private Long eventoId;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private String lugar;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaModificacion;

    @ManyToOne
    @JoinColumn(name="creadorId", nullable = false)
    @JsonIgnore
    private Usuarios eventoCreador;

    @ManyToMany(mappedBy = "eventos")
    @JsonIgnore
    private List<Usuarios> miembrosEvento;

    @ManyToOne
    @JoinColumn(name="tribuId", nullable = false)
    @JsonIgnore
    private Tribus tribu;

    //Getter and setters

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

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Usuarios getEventoCreador() {
        return eventoCreador;
    }

    public void setEventoCreador(Usuarios eventoCreador) {
        this.eventoCreador = eventoCreador;
    }

    public List<Usuarios> getMiembrosEvento() {
        return miembrosEvento;
    }

    public void setMiembrosEvento(List<Usuarios> miembrosEvento) {
        this.miembrosEvento = miembrosEvento;
    }
    public Tribus getTribu() {
        return tribu;
    }

    public void setTribu(Tribus tribu) {
        this.tribu = tribu;
    }
}
