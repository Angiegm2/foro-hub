package com.alura.foro_hub.domain.user.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateProfileDto(
        @NotNull
        String name,
        @NotNull
        String lastName,
        @NotNull
        String country
) {
}
