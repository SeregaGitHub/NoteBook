package ru.kraser.two_token_security.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.kraser.two_token_security.model.Role;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

import static ru.kraser.two_token_security.util.Utilities.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityWebConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // Нужно если в SecurityAppConfiguration есть бин AuthenticationProvider
    //private final AuthenticationProvider authenticationProvider;

    @Value("${allowed.frontend.url}")
    private String allowedFrontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(BASE_URL + AUTH_URL)
                        .permitAll()
                        .requestMatchers(BASE_URL + OPEN_URL)
                        .permitAll()
                        .requestMatchers(BASE_URL + ADMIN_URL + "/**").hasRole(
                                Role.ADMIN.name())
// PostgreSQL: CREATE TYPE user_role AS ENUM ('ADMIN', 'TECHNICIAN', 'EMPLOYEE');
// Class User: @Enumerated(EnumType.STRING) private Role role; public enum Role {ADMIN, TECHNICIAN, EMPLOYEE}

// JwtUserDetails -> getAuthorities() -> list.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
                        .requestMatchers(BASE_URL + TECHNICIAN_URL + "/**").hasAnyRole(
                                Role.ADMIN.name(), Role.TECHNICIAN.name())
// JwtUserDetails -> getAuthorities() -> list.add(new SimpleGrantedAuthority(user.getRole().name()));
//                        .requestMatchers(BASE_URL + TECHNICIAN_URL + "/**").hasAnyAuthority(
//                                Role.ADMIN.name(), Role.TECHNICIAN.name())
                        .requestMatchers(BASE_URL + EMPLOYEE_URL + "/**").hasAnyRole(
                                Role.ADMIN.name(), Role.TECHNICIAN.name(), Role.EMPLOYEE.name())
                        .anyRequest().authenticated())
                .sessionManagement(
                        manager -> manager.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS)
                )
                // Нужно если в SecurityAppConfiguration есть бин AuthenticationProvider
                //.authenticationProvider(authenticationProvider)
                .addFilterAfter(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(List.of(allowedFrontendUrl));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Collections.singletonList("*"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }
}
