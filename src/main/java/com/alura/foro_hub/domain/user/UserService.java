package com.alura.foro_hub.domain.user;


import com.alura.foro_hub.domain.user.dtos.AuthenticationUserDto;
import com.alura.foro_hub.domain.user.dtos.CreateUserDto;
import com.alura.foro_hub.domain.user.dtos.GetProfile;

import com.alura.foro_hub.domain.user.dtos.GetUserWithProfile;
import com.alura.foro_hub.domain.user.model.Profile;
import com.alura.foro_hub.domain.user.model.User;
import com.alura.foro_hub.domain.user.repository.ProfileRepository;
import com.alura.foro_hub.domain.user.repository.RoleRepository;

import com.alura.foro_hub.domain.user.repository.UserRepository;
import com.alura.foro_hub.infra.errors.BadRequestException;
import com.alura.foro_hub.infra.errors.ForbiddenException;
import com.alura.foro_hub.infra.errors.ValidateException;
import com.alura.foro_hub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository,
                       AuthenticationManager authenticationManager, RoleRepository roleRepository, TokenService tokenService){
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.tokenService= tokenService;
    }

    public GetUserWithProfile signUpUser(CreateUserDto payload) {
        var existEmail = this.userRepository.existsByEmail(payload.email());
        if (existEmail) {
            throw new BadRequestException("email (" + payload.email() + ") already exists");
        }
        var role = this.roleRepository.findRoleByName("ROLE_USER")
                .orElseThrow(() -> new ValidateException("role not found"));

        var passwordEncode = bCryptPasswordEncoder.encode(payload.password());
        User user = this.userRepository.save(new User(payload.email(), passwordEncode, role));
        Profile profile = this.profileRepository.save(new Profile(payload.profile(), user));
        return new GetUserWithProfile(user.getId(), user.getEmail(), new GetProfile(profile));
    }

    public User findUserByEmail(String email) {
        return this.userRepository.findUserByEmail(email).orElseThrow(() ->
                new ValidateException("user with email (" + email + ") not found")
        );
    }

    public String signIn(AuthenticationUserDto authenticationUserDto){
        Authentication authenticateUser = null;
        Authentication token = new UsernamePasswordAuthenticationToken(
                authenticationUserDto.email(),
                authenticationUserDto.password()
        );
        try {
            authenticateUser = authenticationManager.authenticate(token);
        }catch (RuntimeException e){
            throw new ForbiddenException("email or password incorrect");
        }
        return tokenService.generateToken((UserDetails) authenticateUser.getPrincipal());
    }
}
