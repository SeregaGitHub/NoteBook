package ru.kraser.two_token_security.security.dto;

import lombok.Builder;
import ru.kraser.two_token_security.model.Role;

@Builder
public record AuthenticationResponse(
        String accessToken,
        String refreshToken,
        String username,
        Role role
) {
}
