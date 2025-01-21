package com.alura.foro_hub.domain.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(
        @NotNull
        @Email
        String email,
        @NotNull
        String password,

        CreateProfileDto profile
) {
}
