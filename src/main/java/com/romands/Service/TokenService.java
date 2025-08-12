package com.romands.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.romands.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String code;

    public String GenerateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(code);
            return JWT.create()
                    .withIssuer("api-Romands")
                    .withSubject(user.getUsername())
                    .withExpiresAt(tokenExperirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException e) {
            return "Erro na criacao JWT";
        }
    }

    public Instant tokenExperirationDate () {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-03:00"));
    }
    public String validadeToken( String string) {
        Algorithm algorithm = Algorithm.HMAC256(code);
        return JWT.require(algorithm)
                .withIssuer("api-Romands")
                .build()
                .verify(string)
                .getSubject();
    }
}
