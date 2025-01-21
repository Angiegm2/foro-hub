package com.alura.foro_hub.domain.user.dtos;

import com.alura.foro_hub.domain.user.model.User;

public record GetUserWithoutProfile(
        Long id,
        String email
) {
    public GetUserWithoutProfile(User user){
        this(user.getId(), user.getEmail());
    }
}
