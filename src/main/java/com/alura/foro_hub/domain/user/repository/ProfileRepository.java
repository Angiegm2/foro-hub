package com.alura.foro_hub.domain.user.repository;

import com.alura.foro_hub.domain.user.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
