package ru.kraser.three_token_security.security;

import org.springframework.stereotype.Service;
import ru.kraser.three_token_security.model.RefreshToken;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryRefreshTokenStorage {

    private final Map<String, RefreshToken> tokens = new ConcurrentHashMap<>();

    public void save(RefreshToken refreshToken) {
        tokens.put(refreshToken.getTokenHash(), refreshToken);
    }

    public Optional<RefreshToken> findByTokenHash(String tokenHash) {
        return Optional.ofNullable(tokens.get(tokenHash));
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
        tokens.remove(tokenHash);
    }
}
