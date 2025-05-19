package com.RobertoPalMol.TribeMe_Backend.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class TribuDTO {
    private Long tribuId;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private List<String> categorias;
    private int numeroMaximoMiembros;
    private boolean esPrivada;
    private LocalDateTime fechaCreacion;
    private String autorId;
    private String autorNombre;

    private List<UsuarioDTO> miembros;


    // Constructor
    public TribuDTO(Long tribuId, String nombre, String descripcion, String imagenUrl, List<String> categorias,
                    int numeroMaximoMiembros, boolean esPrivada, LocalDateTime fechaCreacion,
                    String autorId, String autorNombre,  List<UsuarioDTO> miembros) {
        this.tribuId = tribuId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.categorias = categorias;
        this.numeroMaximoMiembros = numeroMaximoMiembros;
        this.esPrivada = esPrivada;
        this.fechaCreacion = fechaCreacion;
        this.autorId = autorId;
        this.autorNombre = autorNombre;
        this.miembros = miembros;
    }

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

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public int getNumeroMaximoMiembros() {
        return numeroMaximoMiembros;
    }

    public void setNumeroMaximoMiembros(int numeroMaximoMiembros) {
        this.numeroMaximoMiembros = numeroMaximoMiembros;
    }

    public boolean isEsPrivada() {
        return esPrivada;
    }

    public void setEsPrivada(boolean esPrivada) {
        this.esPrivada = esPrivada;
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

    public List<UsuarioDTO> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<UsuarioDTO> miembros) {
        this.miembros = miembros;
    }
}
