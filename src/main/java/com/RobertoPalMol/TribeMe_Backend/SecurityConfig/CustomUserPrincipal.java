package com.RobertoPalMol.TribeMe_Backend.SecurityConfig;



public class CustomUserPrincipal {
    private Long id;
    private String nombre;
    private String correo;

    // Constructor
    public CustomUserPrincipal(Long id, String nombre, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
}
