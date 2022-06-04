package com.weissbeerger.demo.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.weissbeerger.demo.model.User;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class TokenHandler {
    String jwsKey = "somerandomString123456";
    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Optional<Integer> extractUserId(@NonNull String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwsKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            System.out.println(jwt.getId());
            return Optional.ofNullable(jwt.getId()).map(Integer::new);

        }catch (RuntimeException e ){
           return Optional.empty();
        }
    }

    public String generateTokenId(@NonNull Integer id, @NonNull LocalDateTime localDateTime) {
        Algorithm algorithm = Algorithm.HMAC256(jwsKey);
        return JWT.create()
                .withJWTId(id.toString())
                .withExpiresAt(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(algorithm);
    }
    public boolean checkMatchesPasswords(User savedUser, User loginUser){
        PasswordEncoder encoder = bcryptPasswordEncoder();
        return encoder.matches(loginUser.getPassword(),savedUser.getPassword());
    }
}
