package com.alura.foro_hub.controller;

import com.alura.foro_hub.domain.course.CourseService;

import com.alura.foro_hub.domain.course.dtos.CreateCourseDto;
import com.alura.foro_hub.domain.course.dtos.GetCourse;
import com.alura.foro_hub.domain.course.dtos.UpdateCourseDto;
import com.alura.foro_hub.domain.serializer.PageDto;
import com.alura.foro_hub.domain.serializer.PageMetaData;
import com.alura.foro_hub.domain.serializer.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Course")
@RestController
@RequestMapping("/courses")
@SecurityRequirement(name = "bearer-key")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //Crear nuevo curso
    @Operation(summary = "Create new course - Only Admin")
    @PostMapping
    public ResponseEntity<Response> createCourse(@RequestBody @Valid CreateCourseDto payload,
                                                 UriComponentsBuilder uriComponentsBuilder){
        GetCourse course = this.courseService.createCourse(payload);
        URI uri = uriComponentsBuilder.path("/courses/{id}").buildAndExpand(course.id()).toUri();
        return ResponseEntity.created(uri).body(
                new Response(true, course)
        );
    }

    // Obtener todos los cursos
    @Operation(summary = "Get all courses")
    @GetMapping
    public ResponseEntity <PageDto<GetCourse>> getAllCourses(@PageableDefault(
            size = 5
    ) Pageable pageable) {
        Page<GetCourse> page = this.courseService.getAllCourses(pageable);
        PageMetaData<GetCourse> pagination = new PageMetaData<GetCourse>(page);
        return ResponseEntity.ok(
                new PageDto<GetCourse>(
                        page.getContent(),
                        pagination
                ));
    }

    // Obtener un curso por ID
    @Operation(summary = "Get course by id")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getCourse(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new Response(true, this.courseService.getCourse(id))
                );
    }


    // Actualizar un curso existente
    @Operation(summary = "Update course - Only Admin")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Response> updateCourse(@PathVariable Long id, @RequestBody UpdateCourseDto payload) {
        return ResponseEntity.ok().body(
                new Response(true, this.courseService.updateCourse(id, payload))
        );
    }

    // Eliminar un curso
    @Operation(summary = "Delete course - Only Admin")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Response> deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new Response(true, this.courseService.deleteCourse(id))
        );
    }

}
