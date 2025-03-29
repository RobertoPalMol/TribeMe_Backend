package com.RobertoPalMol.TribeMe_Backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Tribus")
public class Tribus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tribuId")
    private Long tribuId;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @ManyToOne
    @JoinColumn(name="creadorId", nullable = false)
    @JsonIgnore
    private Usuarios creador;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fechaModificacion;

    @Column
    private String imagen;

    @Column(nullable = false)
    private int usuariosMaximos;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean tribuPrivada;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean crearEventos;

    @ManyToMany
    @JoinTable(
            name = "tribu_categorias",
            joinColumns = @JoinColumn(name="tribuId"),
            inverseJoinColumns = @JoinColumn(name="categoriaId")
    )
    private List<Categorias> categorias;

    //getters y setters


    public Long getTribuId() {
        return tribuId;
    }

    public void setTribuId(Long tribuId) {
        this.tribuId = tribuId;
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

    public Usuarios getCreador() {
        return creador;
    }

    public void setCreador(Usuarios creador) {
        this.creador = creador;
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

    public int getUsuariosMaximos() {
        return usuariosMaximos;
    }

    public void setUsuariosMaximos(int usuariosMaximos) {
        this.usuariosMaximos = usuariosMaximos;
    }

    public boolean isTribuPrivada() {
        return tribuPrivada;
    }

    public void setTribuPrivada(boolean tribuPrivada) {
        this.tribuPrivada = tribuPrivada;
    }

    public boolean isCrearEventos() {
        return crearEventos;
    }

    public void setCrearEventos(boolean crearEventos) {
        this.crearEventos = crearEventos;
    }

    public List<Categorias> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categorias> categorias) {
        this.categorias = categorias;
    }
}
