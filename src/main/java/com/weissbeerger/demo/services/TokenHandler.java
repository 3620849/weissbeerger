package com.weissbeerger.demo.services;

import com.google.common.io.BaseEncoding;
import com.weissbeerger.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class TokenHandler {
    private final SecretKey secretKey;
    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public TokenHandler( ) {
        String jwsKey = "somerandomString123456";
        byte [] decodedKey = BaseEncoding.base64().decode(jwsKey);
        secretKey = new SecretKeySpec(decodedKey,0,decodedKey.length,"AES");
    }

    public Optional<Integer> extractUserId(@NonNull String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Claims body = claimsJws.getBody();
            return Optional.ofNullable(body.getId()).map(Integer::new);

        }catch (RuntimeException e ){
           return Optional.empty();
        }
    }

    public String generateTokenId(@NonNull int id, @NonNull LocalDateTime localDateTime) {
        return Jwts.builder()
                .setId(""+id)
                .setExpiration(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .compact();
    }
    public boolean checkMatchesPasswords(User savedUser, User loginUser){
        PasswordEncoder encoder = bcryptPasswordEncoder();
        return encoder.matches(loginUser.getPassword(),savedUser.getPassword());
    }
}
