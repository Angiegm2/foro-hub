package com.alura.foro_hub.domain.course.repository;

import com.alura.foro_hub.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
