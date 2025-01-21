package com.alura.foro_hub.domain.serializer;


import java.util.List;

public record PageDto<T>(
        List<T> data,
        PageMetaData<T> page
) {
}
