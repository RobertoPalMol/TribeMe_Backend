package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.DTO.LoginRequest;
import com.RobertoPalMol.TribeMe_Backend.DTO.LoginResponseDTO;
import com.RobertoPalMol.TribeMe_Backend.DTO.SingnUpRequest;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.UsuarioRepository;
import com.RobertoPalMol.TribeMe_Backend.SecurityConfig.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest login) {
        Usuarios usuario = usuarioRepository.findByCorreo(login.getCorreo()).orElse(null);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }

        if (!passwordMatches(login.getContraseña(), usuario.getContraseña())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }

        // Generar el token
        String token = jwtTokenProvider.generateToken(usuario);

        // Crear la respuesta
        LoginResponseDTO response = new LoginResponseDTO();
        response.setUsuarioId(usuario.getUsuarioId());
        response.setCorreo(usuario.getCorreo());
        response.setNombre(usuario.getNombre());
        response.setImagen(usuario.getImagen());
        response.setFechaCreacion(usuario.getFechaCreacion());
        response.setToken(token);

        return ResponseEntity.ok(response);
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

        // Generar el token para el usuario recién creado
        String token = jwtTokenProvider.generateToken(nuevoUsuario);

        // Crear la respuesta
        LoginResponseDTO response = new LoginResponseDTO();
        response.setUsuarioId(nuevoUsuario.getUsuarioId());
        response.setCorreo(nuevoUsuario.getCorreo());
        response.setNombre(nuevoUsuario.getNombre());
        response.setImagen(nuevoUsuario.getImagen());
        response.setFechaCreacion(nuevoUsuario.getFechaCreacion());
        response.setToken(token);

        // Devolver la respuesta con el token
        return ResponseEntity.ok(response);
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }

}
