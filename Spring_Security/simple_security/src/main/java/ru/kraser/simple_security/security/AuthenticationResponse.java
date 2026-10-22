package ru.kraser.simple_security.security;

import lombok.Builder;
import ru.kraser.simple_security.model.Role;

@Builder
public record AuthenticationResponse(
        String jwt,
        String username,
        Role role
) {
}
