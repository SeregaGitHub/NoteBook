package ru.kraser.simple_security.security;

import lombok.Builder;
import ru.kraser.simple_security.model.Role;

@Builder
public record AuthenticationResponse(
        String thJwt,
        String username,
        Role role
) {
}
