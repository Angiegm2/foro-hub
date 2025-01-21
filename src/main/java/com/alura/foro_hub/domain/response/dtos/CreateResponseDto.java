package com.alura.foro_hub.domain.response.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateResponseDto(
        @NotNull
        String message,

        @NotNull
        Long idTopic
) {
}
