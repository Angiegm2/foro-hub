package com.alura.foro_hub.domain.course.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateCourseDto(
        @NotNull
        String name,
        @NotNull
        String category
) {
}
