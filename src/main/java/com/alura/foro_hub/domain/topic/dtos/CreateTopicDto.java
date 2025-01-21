package com.alura.foro_hub.domain.topic.dtos;

import jakarta.validation.constraints.NotNull;

public record CreateTopicDto(
        @NotNull
        String title,
        @NotNull
        String content,
        @NotNull
        Long idCourse
) {
}
