package com.RobertoPalMol.TribeMe_Backend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name="Categorias")
public class Categorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="categoriaId")
    private Long categroiaId;

    @Column(nullable = false)
    private String nombre;

    //getters y setters


    public Long getCategroiaId() {
        return categroiaId;
    }

    public void setCategroiaId(Long categroiaId) {
        this.categroiaId = categroiaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
