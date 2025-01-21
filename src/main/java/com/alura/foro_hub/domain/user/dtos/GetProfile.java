package com.alura.foro_hub.domain.user.dtos;

import com.alura.foro_hub.domain.user.model.Profile;

public record GetProfile(
        String name,
        String lastName,
        String country
) {
    public GetProfile(Profile profile){
        this(profile.getName(), profile.getLastName(), profile.getCountry());
    }
}
