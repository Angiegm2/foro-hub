package com.alura.foro_hub.controller;


import com.alura.foro_hub.domain.serializer.PageDto;
import com.alura.foro_hub.domain.serializer.PageMetaData;
import com.alura.foro_hub.domain.serializer.Response;
import com.alura.foro_hub.domain.serializer.Success;
import com.alura.foro_hub.domain.topic.dtos.CreateTopicDto;
import com.alura.foro_hub.domain.topic.TopicService;
import com.alura.foro_hub.domain.topic.dtos.GetTopicWithAuthor;
import com.alura.foro_hub.domain.topic.dtos.GetTopicWithoutAuthor;
import com.alura.foro_hub.domain.topic.dtos.UpdateTopicDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Topic")
@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @Operation(summary = "Create a new Topic - Only authenticate Users")
    @PostMapping
    public ResponseEntity<Response> createTopic(@RequestBody @Valid CreateTopicDto payload,
                                                UriComponentsBuilder uriComponentsBuilder,
                                                Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        GetTopicWithAuthor topic = this.topicService.createTopic(payload, user.getUsername());
        URI uri = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topic.id()).toUri();
        return ResponseEntity.created(uri).body(new Response(true, topic));
    }

    @Operation(summary = "Get all topics")
    @GetMapping
    public ResponseEntity<PageDto<GetTopicWithoutAuthor>> getAllTopic(
            @PageableDefault(size = 5) Pageable pageable,
            @RequestParam(required = false) String search) {
        Page<GetTopicWithoutAuthor> page;
        if (search != null) {
            page = this.topicService.findByTitle(pageable, search);
        } else {
            page = this.topicService.getAllTopic(pageable);
        }
        PageMetaData<GetTopicWithoutAuthor> pagination = new PageMetaData<GetTopicWithoutAuthor>(page);
        return ResponseEntity.ok(
                new PageDto<GetTopicWithoutAuthor>(
                        page.getContent(),
                        pagination
                ));
    }

    @Operation(summary = "Get topic by id")
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTopic(@PathVariable Long id) {
        return ResponseEntity.ok().body(
                new Response(true, this.topicService.getTopic(id))
        );
    }

    @Operation(summary = "Update topic - Only authenticate Users")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Response> updateTopic(@PathVariable Long id,
                                                @RequestBody UpdateTopicDto payload,
                                                Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok().body(
                new Response(true, this.topicService.updateTopic(id, payload, user.getUsername()))
        );
    }

    @Operation(summary = "Delete topic - Only authenticate Users")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Success> deleteTopic(@PathVariable Long id, Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok().body(this.topicService.deleteTopic(id, user.getUsername()));
    }

    @Operation(summary = "Get all topic by course")
    @GetMapping("/c/{idCourse}")
    public ResponseEntity<PageDto<GetTopicWithoutAuthor>> getTopicByCourse(
            @PageableDefault(size = 5) Pageable pageable,
            @PathVariable Long idCourse) {
        Page<GetTopicWithoutAuthor> page = this.topicService.findByCourseId(pageable, idCourse);

        PageMetaData<GetTopicWithoutAuthor> pagination = new PageMetaData<GetTopicWithoutAuthor>(page);
        return ResponseEntity.ok(
                new PageDto<GetTopicWithoutAuthor>(
                        page.getContent(),
                        pagination
                ));
    }

    @Operation(summary = "Get topic by category course")
    @GetMapping("/category/{category}")
    public ResponseEntity<PageDto<GetTopicWithoutAuthor>> getTopicByCategoryCourse(
            @PageableDefault(size = 5) Pageable pageable,
            @PathVariable String category) {

        Page<GetTopicWithoutAuthor> page = this.topicService.findTopicsByCourseCategory(pageable, category.replace("+", " ").trim());

        PageMetaData<GetTopicWithoutAuthor> pagination = new PageMetaData<GetTopicWithoutAuthor>(page);
        return ResponseEntity.ok(
                new PageDto<GetTopicWithoutAuthor>(
                        page.getContent(),
                        pagination
                ));
    }

    @Operation(summary = "Push solution response to topic")
    @PatchMapping("/{idTopic}/s/{idResponse}")
    @Transactional
    public ResponseEntity<Response> solutionResponse(
            @PathVariable Long idTopic,
            @PathVariable Long idResponse,
            Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        GetTopicWithAuthor topic = this.topicService.solutionResponse(idTopic,idResponse, user.getUsername());
        return ResponseEntity.ok().body(
                new Response(true, topic)
        );
    }
}
