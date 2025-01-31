package com.alura.foro_hub.infra.security;

import com.alura.foro_hub.domain.user.repository.UserRepository;
import com.alura.foro_hub.infra.errors.UnauthorizedException;
import com.alura.foro_hub.infra.errors.ValidateException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    private final UserRepository userRepository;

    @Autowired
    public TokenService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    //@Value("${api.security.secret-jwt}")
    private String JWT_SECRET = "Hola mundo";
    private DecodedJWT decodedJWT;

    public String generateToken(UserDetails userDetails){
        var user = this.userRepository.findUserByEmail(userDetails.getUsername()).
        orElseThrow(() -> new ValidateException("user not found"));
    try {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        return JWT.create()
                .withIssuer("foro-hub")
                .withSubject(user.getEmail())
                .withClaim("id", user.getId())
                .withExpiresAt(generatedDateExpired())
                .sign(algorithm);
        }catch (JWTCreationException exception){
        throw new RuntimeException();
        }
    }

    public Instant generatedDateExpired(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public String getSubject(String token){
        if (token == null){
            throw new UnauthorizedException("token is missing");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("foro-hub")
                    .build();
            decodedJWT = verifier.verify(token);
        }catch (JWTVerificationException exception){
            throw new UnauthorizedException(exception.getMessage());
        }
        if (decodedJWT.getSubject() == null) {
            throw new UnauthorizedException("Verifier invalid");
        }
        return decodedJWT.getSubject();
    }
}
