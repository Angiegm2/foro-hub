package com.alura.foro_hub.domain.serializer;

import org.springframework.data.domain.Page;

public record PageMetaData<T>(
        int current,
        int size,
        boolean first,
        boolean last,
        int total_pages,
        long total_elements
) {
    public PageMetaData(Page<T> page) {
        this(page.getNumber(), page.getSize(), page.isFirst(), page.isLast(), page.getTotalPages(), page.getTotalElements());
    }
}
