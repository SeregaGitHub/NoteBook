package ru.kraser.simple_security.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ru.kraser.simple_security.util.Utilities.AUTH_URL;
import static ru.kraser.simple_security.util.Utilities.BASE_URL;

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
}
