package demo.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desactivar CSRF para pruebas (no recomendado en prod)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permitir todas las rutas temporalmente
            )
            .httpBasic(Customizer.withDefaults()); // Habilita autenticación básica si lo necesitas

        return http.build();
    }
}
