package com.alura.foro_hub.domain.topic.dtos;

import com.alura.foro_hub.domain.course.dtos.GetCourse;
import com.alura.foro_hub.domain.response.dtos.GetResponse;
import com.alura.foro_hub.domain.topic.Topic;

import java.time.Instant;

public record GetTopicWithoutAuthor(
        Long id,
        String title,
        Boolean status,
        GetResponse solution,
        GetCourse course,
        Instant createdOn
) {
    public GetTopicWithoutAuthor(Topic topic) {

        this(topic.getId(),
            topic.getTitle(),
            topic.getStatus(),
            topic.getSolutionResponse() != null ? new GetResponse(topic.getSolutionResponse()) : null,
            new GetCourse(topic.getCourse()),
            topic.getCreatedOn());
    }
}
