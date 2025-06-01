package com.RobertoPalMol.TribeMe_Backend.SecurityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthorizationFilter jwtAuthorizationFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/signup").permitAll()  // Permitir endpoints de login y signup
                        .requestMatchers(HttpMethod.GET, "/api/tribus/imagenes/**").permitAll()//Permitir acceder a las imagenes sin autorización
                        .requestMatchers(HttpMethod.GET, "/api/tribus/mistribus/**").permitAll()//Permitir acceder a mis tribus sin autorización
                        .anyRequest().authenticated()  // El resto requiere autenticación
                )
                .httpBasic(httpBasic -> httpBasic.disable())    // Desactivar autenticación básica
                .formLogin(form -> form.disable());              // Desactivar formularios de login

        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
