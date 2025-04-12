package com.RobertoPalMol.TribeMe_Backend.DTO;

import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;

import java.time.LocalDateTime;
import java.util.List;

public class UsuarioDTO {
    private Long usuarioId;
    private String correo;
    private String nombre;
    private String imagen;
    private LocalDateTime fechaCreacion;
    private List<Tribus> tribus;

    // Constructor
    public UsuarioDTO(Long usuarioId, String correo, String nombre, String imagen, LocalDateTime fechaCreacion) {
        this.usuarioId = usuarioId;
        this.correo = correo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.fechaCreacion = fechaCreacion;
    }

    //getters
    public Long getUsuarioId() { return usuarioId; }
    public String getCorreo() { return correo; }
    public String getNombre() { return nombre; }
    public String getImagen() { return imagen; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    //getter y setter

    public List<Tribus> getTribus() {
        return tribus;
    }

    public void setTribus(List<Tribus> tribus) {
        this.tribus = tribus;
    }
}

