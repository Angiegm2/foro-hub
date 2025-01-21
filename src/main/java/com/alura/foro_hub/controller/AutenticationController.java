package com.alura.foro_hub.controller;


import com.alura.foro_hub.domain.serializer.Response;
import com.alura.foro_hub.domain.user.dtos.AuthenticationUserDto;
import com.alura.foro_hub.domain.user.dtos.CreateUserDto;
import com.alura.foro_hub.domain.user.dtos.GetToken;
import com.alura.foro_hub.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@Tag(name = "Authentication")
@RestController
@RequestMapping("/auth")
public class AutenticationController {

    private final UserService userService;

    @Autowired
    public AutenticationController(UserService userService){
        this.userService = userService;
    }

    //Registrar
    @Operation(summary = "Register user")
    @PostMapping("/sign-up")
    public ResponseEntity<Response> signUpUser(@RequestBody @Valid CreateUserDto payload,
                                               UriComponentsBuilder uriComponentsBuilder) {
        var user = this.userService.signUpUser(payload);
        var uri = uriComponentsBuilder.path("/auth/profile/{id}").buildAndExpand(user.id()).toUri();

        return ResponseEntity.created(uri).body(
                new Response(true, user)
        );
    }

    //login user
    @Operation(summary = "Login user")
    @GetMapping("/sign-in")
    public ResponseEntity<GetToken> signInUser(@RequestBody @Valid AuthenticationUserDto authenticationUserDto){
        var token = this.userService.signIn(authenticationUserDto);
        return ResponseEntity.ok().body(new GetToken(true, token));
    }
}

