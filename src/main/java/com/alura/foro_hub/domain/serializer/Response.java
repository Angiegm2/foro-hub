package com.alura.foro_hub.domain.serializer;

public record Response(
        Boolean success,
        Record data
) {
}
