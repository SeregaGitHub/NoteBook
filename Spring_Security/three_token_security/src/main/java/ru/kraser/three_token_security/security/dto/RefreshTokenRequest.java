package ru.kraser.three_token_security.security.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token обязателен")
        String refreshToken
) {}
