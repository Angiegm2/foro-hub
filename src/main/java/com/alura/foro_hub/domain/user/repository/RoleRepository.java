package com.alura.foro_hub.domain.user.repository;

import com.alura.foro_hub.domain.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByName(String roleUser);
}
