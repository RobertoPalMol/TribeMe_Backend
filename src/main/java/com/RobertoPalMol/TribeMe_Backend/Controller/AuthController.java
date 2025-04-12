package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.DTO.LoginRequest;
import com.RobertoPalMol.TribeMe_Backend.DTO.SingnUpRequest;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest login){
        Usuarios usuario = usuarioRepository.findByCorreo(login.getCorreo()).orElse(null);

        //busca si el correo existe
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo no encontrado");
        }
        //comprueba la contraseña
        if (!passwordMatches(login.getContraseña(), usuario.getContraseña())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
        }
        return ResponseEntity.ok("Login exitoso");
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, hashedPassword);
    }


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SingnUpRequest signupRequest) {
        // Verifica si el correo ya existe
        if (usuarioRepository.findByCorreo(signupRequest.getCorreo()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error: El correo ya está en uso.");
        }

        // Encripta la contraseña
        String hashedPassword = new BCryptPasswordEncoder().encode(signupRequest.getContraseña());

        // Crea el nuevo usuario
        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setCorreo(signupRequest.getCorreo());
        nuevoUsuario.setNombre(signupRequest.getNombre());
        nuevoUsuario.setContraseña(hashedPassword);
        nuevoUsuario.setFechaCreacion(LocalDateTime.now());
        nuevoUsuario.setFechaModificacion(LocalDateTime.now());

        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok("Usuario registrado correctamente");
    }


}
