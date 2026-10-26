package ru.kraser.two_token_security.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

        RefreshToken refreshToken = repository.findByTokenHash(sha256(stringToken))
                .orElseThrow(() -> new AuthException("Your token not found"));

        if (refreshToken.isRevoked()) {
            // REUSE DETECTED — токен уже был обменян, но пришёл снова
            log.warn("REFRESH TOKEN REUSE DETECTED for user '{}', tokenHash='{}. " +
                            "All active tokens for this user will be revoke.",
                    refreshToken.getUsername(), refreshToken.getTokenHash());
// Для production стоит добавить в log IP/User-Agent есть в HttpServletRequest нужна передача из контроллера или RequestContextHolder
            repository.revokeAllTokensByUsername(refreshToken.getUsername());
            //throw new AuthException("Your token reuse detected. Please log in again.");
            // И пользователь и хак получат одно и то же сообщение при попытке использования
            throw new AuthException("Session invalidated. Please log in again.");
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
