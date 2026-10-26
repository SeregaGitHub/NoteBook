package ru.kraser.two_token_security.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import static ru.kraser.two_token_security.util.Utilities.CLAIM_TYPE;

@Service
public class JwtService {

    @Value("${app.secret.key}")
    private String appSecretKey;
    @Value("${access.token.expiration}")
    private long accessTokenExpiration;
    @Value("${refresh.token.expiration}")
    private long refreshTokenExpiration;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails, accessTokenExpiration, "access");
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails, refreshTokenExpiration, "refresh");
    }

    private String createToken(Map<String, Object> extraClaims,
                               UserDetails userDetails,
                               long expiration,
                               String type) {
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim(CLAIM_TYPE, type)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isRefreshToken(String token) {
        return isCorrectTokenType(token, "refresh");
    }

    private boolean isCorrectTokenType(String token, String expectedType) {
        String currentType;
        try {
            currentType = extractClaim(token, c -> c.get(CLAIM_TYPE, String.class));
        } catch (ExpiredJwtException e) {
            // if expired token
            currentType = e.getClaims().get(CLAIM_TYPE, String.class);
        } catch (JwtException | IllegalArgumentException e) {
            // wrong token
            return false;
        }
        return expectedType.equals(currentType);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(appSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
