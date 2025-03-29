package com.RobertoPalMol.TribeMe_Backend.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="Tribus")
public class Tribus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tribuid")
    private Long tribuId;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String descripcion;

    @ManyToOne
    @JoinColumn(name="usuarioId", nullable = false)
    private Usuarios creadorid;

    @Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha_creacion;

    @Column(name = "fecha_modificacion", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha_modificacion;

    @Column
    private String imagen;

    @Column(nullable = false, name = "usuariosmaximos")
    private int usuariosMaximos;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean tribuPrivada;

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean crearEventos;

    @ManyToMany
    @JoinTable(
            name = "tribu_categorias",
            joinColumns = @JoinColumn(name="tribuid"),
            inverseJoinColumns = @JoinColumn(name="categoriaid")
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

    public Usuarios getCreadorid() {
        return creadorid;
    }

    public void setCreadorid(Usuarios creadorid) {
        this.creadorid = creadorid;
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public LocalDateTime getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(LocalDateTime fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
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
