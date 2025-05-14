package com.RobertoPalMol.TribeMe_Backend.SecurityConfig;

import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String SECRET_KEY = "mi_clave_super_secreta_de_32_chars!!";
    private final long validityInMilliseconds = 3600000;  // 1 hora de validez

    // Método para generar el token
    public String generateToken(Usuarios usuario) {
        Claims claims = Jwts.claims();
        claims.setSubject(usuario.getCorreo());
        claims.put("usuarioId", usuario.getUsuarioId());
        claims.put("nombre", usuario.getNombre());
        claims.put("correo", usuario.getCorreo());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    // Método para obtener el nombre de usuario del token
    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    // Método para obtener las claims del token (utilizando el parserBuilder)
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Método para validar el token
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            Date expiration = claims.getExpiration();
            return (expiration != null && !expiration.before(new Date()));
        } catch (Exception e) {
            return false;
        }
    }

    // Método para obtener la autenticación a partir del token
    public Authentication getAuthentication(String token) {
        String username = getUsername(token);
        return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    }
}
