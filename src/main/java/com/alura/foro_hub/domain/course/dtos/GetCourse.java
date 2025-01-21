package com.alura.foro_hub.domain.course.dtos;

import com.alura.foro_hub.domain.course.Course;

public record GetCourse(
        Long id,
        String name,
        String category
) {
    public GetCourse(Course course){
        this(course.getId(), course.getName(), course.getCategory());
    }
}
