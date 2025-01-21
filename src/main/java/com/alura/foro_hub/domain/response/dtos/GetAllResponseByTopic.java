package com.alura.foro_hub.domain.response.dtos;

import com.alura.foro_hub.domain.response.Responses;
import com.alura.foro_hub.domain.user.dtos.GetUserWithoutProfile;

public record GetAllResponseByTopic(
        Long id,
        String message,
        GetUserWithoutProfile author
) {
    public GetAllResponseByTopic(Responses responses){
        this(responses.getId(), responses.getMessage(), new GetUserWithoutProfile(responses.getUser()));
    }
}
