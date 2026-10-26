package ru.kraser.two_token_security.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kraser.two_token_security.exception.AuthException;
import ru.kraser.two_token_security.model.RefreshToken;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    //private final RefreshTokenRepository repository;
    private final InMemoryRefreshTokenStorage repository;
    private final JwtService jwtService;

    // Only for example
    private final AtomicLong idGenerator = new AtomicLong(1);

    public void saveRefreshToken(String username, String stringToken) {

        RefreshToken refreshToken = RefreshToken.builder()
                // Only for example
                .id(idGenerator.incrementAndGet())
                .tokenHash(sha256(stringToken))
                .username(username)
                .expiresAt(jwtService.extractExpiration(stringToken).toInstant())
                .revoked(false)
                .build();

        repository.save(refreshToken);
    }

    public RefreshToken verify(String stringToken) {
        /*// Without check for hacked
        RefreshToken refreshToken = repository.findByTokenHashAndRevokedFalse(sha256(stringToken))
                //.orElseThrow(() -> new AuthException("Your token not found or revoked"));*/

        RefreshToken refreshToken = repository.findByTokenHash(sha256(stringToken))
                .orElseThrow(() -> new AuthException("Your token not found"));

        if (refreshToken.isRevoked()) {
            // REUSE DETECTED — токен уже был обменян, но пришёл снова
            repository.deleteAllTokensByUsername(refreshToken.getUsername());
            // this.revokeAllForUser(refreshToken.getUsername()); // or this
            throw new AuthException("Your token reuse detected. Please log in again.");
        }

        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new AuthException("Your token is expired");
        }
        return refreshToken;
    }

    void revoke(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        repository.save(refreshToken);
    }

    public void revokeAllForUser(String username) {
        repository.deleteAllTokensByUsername(username);
    }

    public void deleteByStringToken(String stringRefreshToken) {
        if (stringRefreshToken == null || stringRefreshToken.isBlank()) {
            return;
        }
        repository.deleteByTokenHash(sha256(stringRefreshToken));
    }

    private String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 algorithm not available", e);
        }
    }
}
