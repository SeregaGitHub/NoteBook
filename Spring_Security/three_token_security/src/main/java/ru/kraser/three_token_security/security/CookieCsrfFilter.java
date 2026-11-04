package ru.kraser.three_token_security.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CookieCsrfFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        // Если так то:
        /*if (csrfToken != null) {
            csrfToken.getToken();
        }*/
/*
        Сделать фронтенд и бэкенд same-origin (рекомендуется для production)

Самый чистый и безопасный способ — избавиться от cross-origin в принципе.
Для этого используется reverse proxy (например, Nginx).

Схема работы:

Nginx слушает один порт (например, 80 или 443) и является единой точкой входа.
Запросы к статике (/, /static/*) проксируются на контейнер с фронтендом.
Запросы к API (/api/*) проксируются на контейнер с бэкендом.
Для браузера всё это будет выглядеть как один origin (например, https://your-app.com).
В этом случае:
document.cookie на фронтенде увидит XSRF-TOKEN, потому что он будет установлен для домена your-app.com.
Паттерн Double-Submit Cookie будет работать без каких-либо изменений в коде.
Проблемы с CORS и SameSite cookie полностью исчезают.
Это стандартная практика для production-развертывания SPA.
*/

        // Для разных origin без nginx (and if cross-origin: frontend and backend on differed ports)
        // Если так то:
        if (csrfToken != null) {
            String token = csrfToken.getToken();
            response.setHeader(csrfToken.getHeaderName(), token);
        }
        /*
        В SecurityWebConfiguration: добавьте этот заголовок в setExposedHeaders вашей CORS-конфигурации,
        чтобы JavaScript на фронтенде мог его прочитать.

        cfg.setExposedHeaders(List.of(CSRF_HEADER)); // "X-XSRF-TOKEN"
        */

        filterChain.doFilter(request, response);
    }
}
