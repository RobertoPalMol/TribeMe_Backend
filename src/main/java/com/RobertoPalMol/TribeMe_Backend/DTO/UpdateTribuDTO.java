package com.RobertoPalMol.TribeMe_Backend.DTO;

import java.util.List;

public class UpdateTribuDTO {
    private String nombre;
    private String descripcion;
    private List<String> categorias;
    private String imagenUrl;
    private int numeroMaximoMiembros;
    private boolean esPrivada;
    private String ubicacion;

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

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
