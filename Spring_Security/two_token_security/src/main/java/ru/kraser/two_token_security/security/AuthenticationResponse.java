package ru.kraser.two_token_security.security;

import lombok.Builder;
import ru.kraser.two_token_security.model.Role;

@Builder
public record AuthenticationResponse(
        String jwt,
        String username,
        Role role
) {
}
