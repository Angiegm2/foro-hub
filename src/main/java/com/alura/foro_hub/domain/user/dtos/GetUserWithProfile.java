package com.alura.foro_hub.domain.user.dtos;

import com.alura.foro_hub.domain.user.model.User;

public record GetUserWithProfile(
        Long id,
        String email,
        GetProfile profile
) {
    public GetUserWithProfile(User user){
    this(user.getId(), user.getEmail(), new GetProfile(user.getProfile()));
    }
}
