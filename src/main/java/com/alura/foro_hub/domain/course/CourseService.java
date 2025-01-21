package com.alura.foro_hub.domain.course;

import com.alura.foro_hub.domain.course.dtos.CreateCourseDto;
import com.alura.foro_hub.domain.course.dtos.GetCourse;
import com.alura.foro_hub.domain.course.dtos.UpdateCourseDto;
import com.alura.foro_hub.domain.course.repository.CourseRepository;
import com.alura.foro_hub.domain.serializer.Success;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public GetCourse createCourse(CreateCourseDto courseDto){
        var course = this.courseRepository.save(new Course(courseDto));
        return new GetCourse(course);
    }

    // Obtener todos los cursos
    public Page<GetCourse> getAllCourses(Pageable pageable){
        return this.courseRepository.findAll(pageable).map(GetCourse::new);
    }

    public Course findCourse(Long id){
        return this.courseRepository.findById(id).orElseThrow(()->
                new ValidationException("course with id (" +id + ") not found")
        );
    }

    public GetCourse getCourse(Long id){
        Course course = this.findCourse(id);
        return new GetCourse(course);
    }

    // Actualizar un curso existente
public GetCourse updateCourse(Long id, UpdateCourseDto payload){
        Course course = this.findCourse(id);
        course.updateCourse(payload);
        return new GetCourse(course);
}
    // Eliminar un curso

    public Success deleteCourse(Long id){
        Course course = this.findCourse(id);
        this.courseRepository.delete(course);
        return new Success(true, "course with id (" + id + ") deleted successfully");
    }

}
