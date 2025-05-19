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

    // Constructor completo
    public UsuarioDTO(Long usuarioId, String correo, String nombre, String imagen, LocalDateTime fechaCreacion) {
        this.usuarioId = usuarioId;
        this.correo = correo;
        this.nombre = nombre;
        this.imagen = imagen;
        this.fechaCreacion = fechaCreacion;
    }

    //getters y setters
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}

