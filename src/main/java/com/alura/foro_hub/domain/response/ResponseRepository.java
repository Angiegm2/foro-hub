package com.alura.foro_hub.domain.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Responses, Long> {
    Page<Responses> findByTopicId(Pageable page, Long id);
}
