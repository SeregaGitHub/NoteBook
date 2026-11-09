package ru.kraser.three_token_security.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import ru.kraser.three_token_security.model.Role;

import java.util.Collections;
import java.util.List;

import static ru.kraser.three_token_security.util.Utilities.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityWebConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    // Нужно если в SecurityAppConfiguration есть бин AuthenticationProvider
    //private final AuthenticationProvider authenticationProvider;

    @Value("${allowed.frontend.url}")
    private String allowedFrontendUrl;

    //  only if no nginx (and if cross-origin: frontend and backend on differed ports)  //
    @Value("${app.cookie.csrf-same-site:Strict}")
    private String csrfCookieSameSite;

    @Value("${app.cookie.csrf-secure:false}")
    private boolean csrfCookieSecure;
    //  only if no nginx (and if cross-origin: frontend and backend on differed ports)  //

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                // прописываем все url которым нужен CSRF токен
                // login CSRF: OWASP рекомендует защищать все POST-эндпоинты, включая логин - тут отключена
                /*
                    нужно, чтобы жертва залогинилась под аккаунтом атакующего,
                    что требует от жертвы ввода пароля атакующего — маловероятно
                 */
                .csrf(csrf -> csrf
                        .csrfTokenRepository(csrfRepository())
                    // для prod можно рассмотреть XorCsrfTokenRequestAttributeHandler() - защита от BREACH-атак
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy())
                        .requireCsrfProtectionMatcher(request -> {
                            // CSRF проверяется ТОЛЬКО для refresh и logout
                            String path = request.getServletPath();
                            String method = request.getMethod();
                            boolean isProtectedPath = path.equals(BASE_URL + AUTH_URL + REFRESH_URL)
                                    || path.equals(BASE_URL + AUTH_URL + LOGOUT_URL);
                            boolean isUnsafeMethod = !HttpMethod.GET.matches(method)
                                    && !HttpMethod.HEAD.matches(method)
                                    && !HttpMethod.OPTIONS.matches(method)
                                    && !HttpMethod.TRACE.matches(method);
                            return isProtectedPath && isUnsafeMethod;
                        })
                )
                // другой вариант
                // прописываем все url которым не нужен CSRF токен

                /*.csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                        .ignoringRequestMatchers(
                                BASE_URL + AUTH_URL,
                                BASE_URL + OPEN_URL,
                                BASE_URL + ADMIN_URL + "/**",
                                BASE_URL + TECHNICIAN_URL + "/**",
                                BASE_URL + EMPLOYEE_URL + "/**",
                                BASE_URL + DEFAULT_URL + "/**"
                        )
                )*/
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(BASE_URL + AUTH_URL)
                        .permitAll()
                        .requestMatchers(BASE_URL + AUTH_URL + REFRESH_URL)
                        .permitAll()
                        .requestMatchers(BASE_URL + AUTH_URL + LOGOUT_URL)
                        .permitAll()
                        .requestMatchers(BASE_URL + OPEN_URL)
                        .permitAll()
                        .requestMatchers(BASE_URL + ADMIN_URL + "/**").hasRole(
                                Role.ADMIN.name())
                        .requestMatchers(BASE_URL + TECHNICIAN_URL + "/**").hasAnyRole(
                                Role.ADMIN.name(), Role.TECHNICIAN.name())
                        .requestMatchers(BASE_URL + EMPLOYEE_URL + "/**").hasAnyRole(
                                Role.ADMIN.name(), Role.TECHNICIAN.name(), Role.EMPLOYEE.name())
                        .anyRequest().authenticated())
                .sessionManagement(
                        manager -> manager
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Нужно если в SecurityAppConfiguration есть бин AuthenticationProvider
                //.authenticationProvider(authenticationProvider)
                .addFilterAfter(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .addFilterAfter(
                        new CookieCsrfFilter(), UsernamePasswordAuthenticationFilter.class
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
                cfg.setExposedHeaders(List.of(CSRF_COOKIE));   // чтобы клиент мог прочитать XSRF-TOKEN
                cfg.setAllowCredentials(true);                 // обязательно для cookie
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    private CookieCsrfTokenRepository csrfRepository() {
        CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        csrfTokenRepository.setCookieName(CSRF_COOKIE);
        csrfTokenRepository.setHeaderName(CSRF_HEADER);

        // only if no nginx (and if cross-origin: frontend and backend on differed ports) //
        csrfTokenRepository.setCookieCustomizer(cookie -> cookie
                .sameSite(csrfCookieSameSite)
                .secure(csrfCookieSecure));
        // only if no nginx (and if cross-origin: frontend and backend on differed ports) //

        return csrfTokenRepository;
    }
}
