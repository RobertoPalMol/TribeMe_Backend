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

    // Constructor
    public TribuDTO(Long tribuId, String nombre, String descripcion, String imagenUrl, List<String> categorias,
                    int numeroMaximoMiembros, boolean esPrivada, LocalDateTime fechaCreacion,
                    String autorId, String autorNombre) {
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
    }

    // Getters
    public Long getTribuId() {
        return tribuId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public int getNumeroMaximoMiembros() {
        return numeroMaximoMiembros;
    }

    public boolean esPrivada() {
        return esPrivada;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getAutorId() {
        return autorId;
    }

    public String getAutorNombre() {
        return autorNombre;
    }

    // Setters
    public void setTribuId(Long tribuId) {
        this.tribuId = tribuId;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public void setNumeroMaximoMiembros(int numeroMaximoMiembros) {
        this.numeroMaximoMiembros = numeroMaximoMiembros;
    }

    public void setEsPrivada(boolean esPrivada) {
        this.esPrivada = esPrivada;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setAutorId(String autorId) {
        this.autorId = autorId;
    }

    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }
}
