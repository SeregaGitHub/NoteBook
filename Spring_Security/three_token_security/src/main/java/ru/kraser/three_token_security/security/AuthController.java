package ru.kraser.three_token_security.security;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kraser.three_token_security.exception.AuthException;
import ru.kraser.three_token_security.security.dto.AuthenticationRequest;
import ru.kraser.three_token_security.security.dto.AuthenticationResponse;

import java.time.Duration;

import static ru.kraser.three_token_security.util.Utilities.*;

@RestController
@RequestMapping(value = BASE_URL)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;

    @Value("${app.cookie.refresh-name:" + REFRESH_TOKEN_COOKIE + "}")
    private String refreshCookieName;

    @Value("${app.cookie.refresh-path:" + BASE_URL + AUTH_URL + "/}")
    private String refreshCookiePath;

    @Value("${app.cookie.secure:false}")
    private boolean cookieSecure;

    @Value("${app.cookie.same-site:Strict}")
    private String cookieSameSite;

    @Value("${refresh.token.expiration}")
    private long refreshTokenExpiration;

    @PostMapping(value = AUTH_URL)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Validated() @RequestBody AuthenticationRequest request, HttpServletResponse response) {
        log.info("Authenticating User with name - {}...", request.username());
        AuthenticationService.AuthResult authResult = authenticationService.authenticate(request);
        // refresh token -> HttpOnly cookie
        ResponseCookie refreshCookie = buildRefreshCookie(authResult.refreshToken());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());
        log.info("User with name - {}, successfully authenticated", request.username());
        return ResponseEntity.ok(authResult.apiResponse());
    }

    @PostMapping(AUTH_URL + REFRESH_URL)
    public ResponseEntity<AuthenticationResponse> refresh(
            @CookieValue(name = "${app.cookie.refresh-name:" + REFRESH_TOKEN_COOKIE + "}", required = false)
            String refreshToken,
            HttpServletResponse response) {

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new AuthException("Refresh token cookie is missing");
        }

        log.info("Updating refresh token...");
        AuthenticationService.AuthResult authResult = authenticationService.refreshToken(refreshToken);

        // ротация: новый refresh -> новая cookie
        response.addHeader(HttpHeaders.SET_COOKIE, buildRefreshCookie(authResult.refreshToken()).toString());

        log.info("Refresh token successfully updated");
        return ResponseEntity.ok(authResult.apiResponse());
        /*
                  //  Вместо HttpServletResponse response - можно так:
          return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,
                        buildRefreshCookie(authResult.refreshToken()).toString())
                .body(authResult.apiResponse());*/
    }

    @PostMapping(AUTH_URL + LOGOUT_URL)
    public ResponseEntity<Void> logout(
            @CookieValue(name = "${app.cookie.refresh-name:" + REFRESH_TOKEN_COOKIE + "}", required = false)
            String refreshToken,
            HttpServletResponse response) {

        log.info("Attempting to logout...");
        authenticationService.logout(refreshToken);

        // удаляем cookie
        ResponseCookie deleteCookie = ResponseCookie.from(refreshCookieName, "")
                .httpOnly(true)
                .secure(cookieSecure)
                .path(refreshCookiePath)
                .sameSite(cookieSameSite)
                .maxAge(Duration.ZERO)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        return ResponseEntity.noContent().build();
    }

    private ResponseCookie buildRefreshCookie(String refreshToken) {
        return ResponseCookie.from(refreshCookieName, refreshToken)
                .httpOnly(true)                       // JS не прочитает
                .secure(cookieSecure)                 // только HTTPS в prod
                .path(refreshCookiePath)              // отправляется только на /api/v1/auth/*
                .sameSite(cookieSameSite)             // защита от CSRF
                .maxAge(Duration.ofMillis(refreshTokenExpiration))
                .build();
    }
}
