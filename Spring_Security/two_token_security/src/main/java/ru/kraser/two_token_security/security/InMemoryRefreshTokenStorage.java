package ru.kraser.two_token_security.security;

import org.springframework.stereotype.Service;
import ru.kraser.two_token_security.model.RefreshToken;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryRefreshTokenStorage {

    private final Map<Long, RefreshToken> tokens = new ConcurrentHashMap<>();

    public void save(RefreshToken refreshToken) {
        tokens.put(refreshToken.getId(), refreshToken);
    }

    public Optional<RefreshToken> findByTokenHash(String tokenHash) {

        return tokens.values()
                .stream()
                .filter(v -> Objects.equals(v.getTokenHash(), tokenHash))
                .findFirst();
    }

    // logout from all devices
    public void revokeAllTokensByUsername(String username) {

        tokens.values().forEach(token -> {
            if (token.getUsername().equals(username)) {
                token.setRevoked(true);
            }
        });
    }

    public void deleteByTokenHash(String tokenHash) {
        tokens.values().removeIf(token -> tokenHash.equals(token.getTokenHash()));
    }
}
