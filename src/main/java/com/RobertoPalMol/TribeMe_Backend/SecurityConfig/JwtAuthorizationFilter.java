package com.RobertoPalMol.TribeMe_Backend.SecurityConfig;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    // Dependencia del proveedor de tokens JWT
    private final JwtTokenProvider tokenProvider;

    // Constructor que inyecta el JwtTokenProvider
    public JwtAuthorizationFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    // Método que se ejecuta una vez por cada solicitud HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Intenta extraer el token JWT de la solicitud
        String token = resolveToken(request);

        // Si se encuentra un token y es válido, se procesa la autenticación
        if (token != null && tokenProvider.validateToken(token)) {
            // Se obtiene la autenticación asociada al token
            Authentication authentication = tokenProvider.getAuthentication(token);
            // Se establece la autenticación en el contexto de seguridad de Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Se continúa con el siguiente filtro en la cadena de filtros
        filterChain.doFilter(request, response);
    }

    // Método auxiliar para resolver y obtener el token JWT del encabezado de autorización
    private String resolveToken(HttpServletRequest request) {
        // Se obtiene el encabezado de autorización
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        // Si el encabezado está presente y comienza con "Bearer ", se extrae el token
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Se elimina el prefijo "Bearer "
        }
        // Si no se encuentra un token válido, se retorna null
        return null;
    }
}
