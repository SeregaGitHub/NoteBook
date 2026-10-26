package ru.kraser.two_token_security.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kraser.two_token_security.security.dto.AuthenticationRequest;
import ru.kraser.two_token_security.security.dto.AuthenticationResponse;
import ru.kraser.two_token_security.security.dto.RefreshTokenRequest;

import static ru.kraser.two_token_security.util.Utilities.*;

@RestController
@RequestMapping(value = BASE_URL)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = AUTH_URL)
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Validated() @RequestBody AuthenticationRequest request) {
        log.info("Authenticating User with name - {}", request.username());
        ResponseEntity<AuthenticationResponse> response =
                ResponseEntity.ok(authenticationService.authenticate(request));
        log.info("User with name - {}, successfully authenticated", request.username());
        return response;
    }

    @PostMapping(AUTH_URL + REFRESH_URL)
    public ResponseEntity<AuthenticationResponse> refresh(
            @Validated() @RequestBody RefreshTokenRequest request) {
        log.info("Refreshing token...");
        ResponseEntity<AuthenticationResponse> response =
                ResponseEntity.ok(authenticationService.refreshToken(request));
        log.info("Token successfully refreshed");
        return response;
    }

    @PostMapping(AUTH_URL + LOGOUT_URL)
    public ResponseEntity<Void> logout(@Validated() @RequestBody RefreshTokenRequest request) {
        log.info("Attempting to logout...");
        authenticationService.logout(request.refreshToken());
        log.info("Logout successfully completed");
        return ResponseEntity.noContent().build();
    }
}
