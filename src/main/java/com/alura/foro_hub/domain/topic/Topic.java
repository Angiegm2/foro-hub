package com.alura.foro_hub.domain.topic;

import com.alura.foro_hub.domain.course.Course;
import com.alura.foro_hub.domain.response.Responses;
import com.alura.foro_hub.domain.topic.dtos.UpdateTopicDto;
import com.alura.foro_hub.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "topics")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Responses> responses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "solution_response_id", referencedColumnName = "id")
    private Responses solutionResponse;

    @CreationTimestamp
    private Instant CreatedOn;
    @UpdateTimestamp
    private Instant lastUpdateOn;

    public Topic(Course course, String content, String title, User user){
        this.status = true;
        this.course = course;
        this.content = content;
        this.title = title;
        this.user = user;
    }

    public void updateTopic(UpdateTopicDto payload){
        if (payload.title() != null){
            this.title = payload.title();
        } else if (payload.content() != null) {
            this.content = payload.content();
        }
    }
    public void changeStatus(){
        this.status = !this.status;
    }

    public void addSolutionResponse(Responses responses){
        this.solutionResponse = responses;
        this.status = false;
    }
}
