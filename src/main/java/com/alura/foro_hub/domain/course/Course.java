package com.alura.foro_hub.domain.course;


import com.alura.foro_hub.domain.course.dtos.CreateCourseDto;
import com.alura.foro_hub.domain.course.dtos.UpdateCourseDto;
import com.alura.foro_hub.domain.topic.Topic;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "category")
    private String category;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Topic> topic;

    public Course(CreateCourseDto courseDto){
        this.name = courseDto.name();
        this.category = courseDto.category();
    }

    public void updateCourse(UpdateCourseDto payload){
        if(payload.name() != null){
            this.name = payload.name();
        } else if (payload.category() != null) {
            this.category = payload.category();
        }
    }

}
