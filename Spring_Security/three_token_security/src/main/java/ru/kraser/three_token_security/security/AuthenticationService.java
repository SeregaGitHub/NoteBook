package ru.kraser.three_token_security.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.kraser.three_token_security.exception.AuthException;
import ru.kraser.three_token_security.model.RefreshToken;
import ru.kraser.three_token_security.model.User;
import ru.kraser.three_token_security.security.dto.AuthenticationRequest;
import ru.kraser.three_token_security.security.dto.AuthenticationResponse;
import ru.kraser.three_token_security.security.dto.RefreshTokenRequest;
import ru.kraser.three_token_security.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
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
            throw new AuthException("Неверное имя пользователя или пароль !!!");
        }

        // User user = authenticationClient.authenticate(request); // If UserService.class in another module
        User user = userService.getUserByName(request.username());
        JwtUserDetails userDetails = new JwtUserDetails(user);

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        refreshTokenService.saveRefreshToken(user.getUsername(), refreshToken);

        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String stringToken = request.refreshToken();

        if (!jwtService.isRefreshToken(stringToken)) {
            throw new AuthException("Invalid token type");
        }

        RefreshToken oldRefreshToken = refreshTokenService.verify(stringToken);

        String username = oldRefreshToken.getUsername();
        User user = userService.getUserByName(username);
        if (user == null) throw new AuthException("User not found");
        JwtUserDetails userDetails = new JwtUserDetails(user);

        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);
        refreshTokenService.saveRefreshToken(username, newRefreshToken);

        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    public void logout(String stringRefreshToken) {
        refreshTokenService.deleteByStringToken(stringRefreshToken);
    }
}
