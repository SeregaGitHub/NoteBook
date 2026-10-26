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

@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {

    //private final RefreshTokenRepository repository;
    private final InMemoryRefreshTokenStorage repository;
    private final JwtService jwtService;

    public void saveRefreshToken(String username, String stringToken) {

        String tokenHash = sha256(stringToken);

        RefreshToken refreshToken = RefreshToken.builder()
                // Only for example
                .id(tokenHash)
                .tokenHash(tokenHash)
                .username(username)
                .expiresAt(jwtService.extractExpiration(stringToken).toInstant())
                .revoked(false)
                .build();

        repository.save(refreshToken);
    }

    /*
    Чтобы при использовании метода revoke() в verify() не возникло - race condition

    В PROD когда будет JpaRepository - метод findByTokenHash() аннотируется @Lock(LockModeType.PESSIMISTIC_WRITE)
    при запросах JPA и @Query (с помощью JPQL)
    Если nativeQuery = true то в конец тела запроса надо добавить - FOR UPDATE
    Тогда до завершения транзакции выбранная стока будет заблокирована

    В сервисе метод verify() аннотируется @Transactional

    (Или делать Атомарный UPDATE-запрос)
     */
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
        revoke(refreshToken);
        return refreshToken;
    }

    private void revoke(RefreshToken refreshToken) {
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
