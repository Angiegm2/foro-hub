package com.alura.foro_hub.controller;



import com.alura.foro_hub.domain.response.ResponseService;
import com.alura.foro_hub.domain.response.dtos.CreateResponseDto;
import com.alura.foro_hub.domain.response.dtos.GetResponse;
import com.alura.foro_hub.domain.response.dtos.UpdateResponseDto;
import com.alura.foro_hub.domain.serializer.PageDto;
import com.alura.foro_hub.domain.serializer.PageMetaData;
import com.alura.foro_hub.domain.serializer.Response;
import com.alura.foro_hub.domain.serializer.Success;
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

@Tag(name = "Response")
@RestController
@RequestMapping("/response")
@SecurityRequirement(name = "bearer-key")
public class ResponseController {
    private final ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @Operation(summary = "Create new answer - Only authenticate User")
    @PostMapping
    public ResponseEntity<Response> createResponse(@RequestBody @Valid CreateResponseDto payload,
                                                   UriComponentsBuilder uriComponentsBuilder,
                                                   Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        GetResponse response = this.responseService.createResponse(payload, user.getUsername());
        URI uri = uriComponentsBuilder.path("/answers/t/{id}").buildAndExpand(payload.idTopic()).toUri();
        return ResponseEntity.created(uri).body(
                new Response(true, response)
        );
    }

    @Operation(summary = "Get all answers by topic")
    @GetMapping("/t/{idTopic}")
    ResponseEntity<PageDto<GetResponse>> getAllAnswerByTopic(@PageableDefault(
            size = 5
    ) Pageable pageable, @PathVariable Long idTopic) {
        Page<GetResponse> page = this.responseService.getAllResponseByTopic(pageable, idTopic);
        PageMetaData<GetResponse> pagination = new PageMetaData<>(page);
        return ResponseEntity.ok(
                new PageDto<GetResponse>(
                        page.getContent(),
                        pagination
                ));
    }

    @Operation(summary = "Update answer - Only authenticate User")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Response> updateAnswer(@PathVariable Long id,
                                                 @RequestBody UpdateResponseDto payload,
                                                 Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok().body(
                new Response(true, this.responseService.updateResponse(id, payload, user.getUsername()))
        );
    }

    @Operation(summary = "Delete answer - Only authenticate User")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Success> deleteAnswer(@PathVariable Long id, Authentication authentication) {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok().body(this.responseService.deleteResponse(id, user.getUsername()));
    }
}
