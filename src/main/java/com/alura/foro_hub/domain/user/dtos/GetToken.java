package com.alura.foro_hub.domain.user.dtos;

public record GetToken(
        Boolean success,
        String accessToken
) {
}
