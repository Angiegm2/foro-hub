package com.alura.foro_hub.domain.user.repository;

import com.alura.foro_hub.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);
}
