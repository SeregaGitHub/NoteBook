package ru.kraser.two_token_security.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.kraser.two_token_security.model.User;
import ru.kraser.two_token_security.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );
        } catch (AuthenticationException e) {
            //throw new AuthException("Неверное имя пользователя или пароль !!!"); // Catch in ErrorHandler.class
            throw new RuntimeException("Неверное имя пользователя или пароль !!!");
        }

        // User user = authenticationClient.authenticate(request); // If UserService.class in another module
        User user = userService.getUserByName(request.username());

        var jwtToken = jwtService.generateToken(new JwtUserDetails(user));

        return AuthenticationResponse.builder()
                .jwt(jwtToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
