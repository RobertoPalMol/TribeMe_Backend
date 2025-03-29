package com.RobertoPalMol.TribeMe_Backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Categorias")
public class Categorias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="categoriaId")
    private Long categroiaId;

    @Column(unique = true,nullable = false)
    private String nombre;

    @ManyToMany(mappedBy = "categorias")
    @JsonIgnore
    private List<Tribus> tribus;

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

    public List<Tribus> getTribus() {
        return tribus;
    }

    public void setTribus(List<Tribus> tribus) {
        this.tribus = tribus;
    }
}
