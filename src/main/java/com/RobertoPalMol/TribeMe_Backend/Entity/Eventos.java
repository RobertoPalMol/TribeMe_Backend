package com.RobertoPalMol.TribeMe_Backend.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Eventos")
public class Eventos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="eventoId")
    private Long eventoId;


}
