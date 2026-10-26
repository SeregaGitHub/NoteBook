package ru.kraser.two_token_security.model;

import lombok.*;

import java.time.Instant;

//@Entity
//@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    //@Id
    //GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(nullable = false, unique = true)
    private String tokenHash;        // храним SHA-256 хеш, а не сам токен

    //@Column(nullable = false)
    private String username;

    //@Column(nullable = false)
    private Instant expiresAt;

    //@Column(nullable = false)
    private boolean revoked = false;

    //@Column(nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();

    /*
                        For storage Postgresql  (or in Redis)

    public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

        Optional<RefreshToken> findByTokenHash(String tokenHash);

        Optional<RefreshToken> findByTokenHashAndRevokedFalse(String tokenHash);

        void deleteByUsername(String username);  // для logout «со всех устройств»
}
     */
}
