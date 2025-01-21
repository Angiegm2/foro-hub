package com.alura.foro_hub.domain.course.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateCourseDto(
        String name,
        String category
) {
}
