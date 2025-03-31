package com.RobertoPalMol.TribeMe_Backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuarioId")
    private Long usuarioId;

    @Column(unique = true, nullable = false)
    private String correo;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String contraseña;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaModificacion;

    @Column
    private String imagen;

    @OneToMany(mappedBy = "tribuCreador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Tribus> tribusCreadas;

    @OneToMany(mappedBy = "eventoCreador", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Eventos> eventosCreados;

    @ManyToMany
    @JoinTable(
            name = "usuario_tribu",
            joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name="tribu_id")
    )
    @JsonIgnore
    private List<Tribus> tribus;

    @ManyToMany
    @JoinTable(
            name = "usuario_evento",
            joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn(name="evento_id")
    )
    @JsonIgnore
    private List<Eventos> eventos;


    // Getters and Setters


    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Tribus> getTribusCreadas() {
        return tribusCreadas;
    }

    public void setTribusCreadas(List<Tribus> tribusCreadas) {
        this.tribusCreadas = tribusCreadas;
    }

    public List<Eventos> getEventosCreados() {
        return eventosCreados;
    }

    public void setEventosCreados(List<Eventos> eventosCreados) {
        this.eventosCreados = eventosCreados;
    }

    public List<Tribus> getTribus() {
        return tribus;
    }

    public void setTribus(List<Tribus> tribus) {
        this.tribus = tribus;
    }

    public List<Eventos> getEventos() {
        return eventos;
    }

    public void setEventos(List<Eventos> eventos) {
        this.eventos = eventos;
    }
}
