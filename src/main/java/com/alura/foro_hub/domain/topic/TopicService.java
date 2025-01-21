package com.alura.foro_hub.domain.topic;

import com.alura.foro_hub.domain.course.Course;
import com.alura.foro_hub.domain.course.CourseService;
import com.alura.foro_hub.domain.response.ResponseRepository;
import com.alura.foro_hub.domain.response.Responses;
import com.alura.foro_hub.domain.serializer.Success;
import com.alura.foro_hub.domain.topic.dtos.CreateTopicDto;
import com.alura.foro_hub.domain.topic.dtos.GetTopicWithAuthor;
import com.alura.foro_hub.domain.topic.dtos.GetTopicWithoutAuthor;
import com.alura.foro_hub.domain.topic.dtos.UpdateTopicDto;
import com.alura.foro_hub.domain.user.UserService;
import com.alura.foro_hub.infra.errors.ForbiddenException;
import com.alura.foro_hub.infra.errors.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final ResponseRepository responseRepository;
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public TopicService(TopicRepository topicRepository, CourseService courseService,
                        UserService userService, ResponseRepository responseRepository
    ) {
        this.responseRepository = responseRepository;
        this.topicRepository = topicRepository;
        this.courseService = courseService;
        this.userService = userService;
    }

    public GetTopicWithAuthor createTopic(CreateTopicDto payload, String email) {
        var user = this.userService.findUserByEmail(email);
        Course course = this.courseService.findCourse(payload.idCourse());
        Topic topic = this.topicRepository.save(new Topic(course, payload.content(), payload.title(), user));
        return new GetTopicWithAuthor(topic);
    }

    public Page<GetTopicWithoutAuthor> getAllTopic(Pageable pageable) {
        return this.topicRepository.findAll(pageable).map(GetTopicWithoutAuthor::new);
    }

    public Page<GetTopicWithoutAuthor> findByTitle(Pageable pageable, String title) {
        return this.topicRepository.findByTitleContainsIgnoreCase(pageable, title).map(GetTopicWithoutAuthor::new);
    }

    public Topic findTopic(Long id) {
        return this.topicRepository.findById(id).orElseThrow(() ->
                new ValidateException("topic with id (" + id + ") not found")
        );
    }

    public GetTopicWithAuthor getTopic(Long id) {
        Topic topic = this.findTopic(id);
        return new GetTopicWithAuthor(topic);
    }

    public GetTopicWithAuthor updateTopic(Long id, UpdateTopicDto payload, String email) {
        Topic topic = this.findTopic(id);
        verifyAdminOrAuthor(topic, email);

        topic.updateTopic(payload);
        return new GetTopicWithAuthor(topic);
    }

    public Success deleteTopic(Long id, String email) {
        Topic topic = this.findTopic(id);
        verifyAdminOrAuthor(topic, email);

        this.topicRepository.delete(topic);
        return new Success(true, "topic with id (" + id + ") deleted");
    }

    public Page<GetTopicWithoutAuthor> findByCourseId(Pageable pageable, Long idCourse) {
        return this.topicRepository.findByCourseId(pageable, idCourse).map(GetTopicWithoutAuthor::new);
    }


    public void verifyAdminOrAuthor(Topic topic, String email) {
        var user = this.userService.findUserByEmail(email);
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
        if (!isAdmin && !topic.getUser().equals(user)) {
            throw new ForbiddenException("permission denied");
        }
    }


    public Page<GetTopicWithoutAuthor> findTopicsByCourseCategory(Pageable pageable, String category) {
        return this.topicRepository.findTopicsByCourseCategory(pageable, category).map(GetTopicWithoutAuthor::new);
    }

    public GetTopicWithAuthor solutionResponse(Long idTopic, Long idResponse, String email) {
        Topic topic = this.findTopic(idTopic);
        verifyAdminOrAuthor(topic, email);
        Responses responses = this.responseRepository.findById(idResponse).orElseThrow(() ->
                new ValidateException("response with id (" + idResponse + ") not found"));
        var isResponseValid = topic.getResponses().stream().anyMatch(a -> a.equals(responses));
        if (!isResponseValid) {
            throw new ValidateException("answer with id (" + idResponse + ") not found");
        }
        topic.addSolutionResponse(responses);
        responses.isSolution();
        return new GetTopicWithAuthor(topic);
    }

}
