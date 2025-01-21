package com.alura.foro_hub.domain.response;

import com.alura.foro_hub.domain.response.dtos.CreateResponseDto;
import com.alura.foro_hub.domain.response.dtos.GetResponse;
import com.alura.foro_hub.domain.response.dtos.UpdateResponseDto;
import com.alura.foro_hub.domain.serializer.Success;
import com.alura.foro_hub.domain.topic.TopicService;
import com.alura.foro_hub.domain.user.UserService;
import com.alura.foro_hub.infra.errors.ForbiddenException;
import com.alura.foro_hub.infra.errors.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final TopicService topicService;
    private final UserService userService;

    @Autowired
    public ResponseService (ResponseRepository responseRepository, TopicService topicService, UserService userService){
        this.responseRepository = responseRepository;
        this.topicService = topicService;
        this.userService = userService;
    }

    public GetResponse createResponse(CreateResponseDto payload, String email){
        var user = this.userService.findUserByEmail(email);

        var topic = this.topicService.findTopic(payload.idTopic());
        Responses responses = this.responseRepository.save(new Responses(payload.message(), topic, user));
        return new GetResponse(responses);
    }

    public Page<GetResponse> getAllResponseByTopic(Pageable pageable, Long idTopic) {
        var topic = this.topicService.findTopic(idTopic);
        return this.responseRepository.findByTopicId(pageable, topic.getId()).map(GetResponse::new);
    }

    public Responses findResponse(Long id) {
        return this.responseRepository.findById(id).orElseThrow(() ->
                new ValidateException("response with id (" + id + ") not found")
        );
    }

    public GetResponse updateResponse(Long id, UpdateResponseDto payload, String email) {
        Responses responses = this.findResponse(id);
        verifyAuthor(responses, email);

        responses.updateResponse(payload);
        return new GetResponse(responses);
    }

    public Success deleteResponse(Long id, String email) {
        Responses responses = this.findResponse(id);
        verifyAuthor(responses, email);

        this.responseRepository.delete(responses);
        return new Success(true, "response with id (" + id + ") deleted");
    }

    public void verifyAuthor(Responses responses, String email) {
        var user = this.userService.findUserByEmail(email);
        if (responses.getUser() != user) {
            throw new ForbiddenException("permission denied.");
        }
    }
}
