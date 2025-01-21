package com.alura.foro_hub.domain.response;

import com.alura.foro_hub.domain.response.dtos.UpdateResponseDto;
import com.alura.foro_hub.domain.topic.Topic;
import com.alura.foro_hub.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(name = "responses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Responses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private Boolean solution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant lastUpdatedOn;

    public Responses(String message, Topic topic, User user) {
        this.solution = false;
        this.message = message;
        this.topic = topic;
        this.user = user;
    }

    public void updateResponse(UpdateResponseDto payload) {
        if (payload.message() != null) {
            this.message = payload.message();
        }
    }

    public void isSolution() {
        this.solution = true;
    }
}
