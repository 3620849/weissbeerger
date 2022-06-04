package com.weissbeerger.demo.services;

import com.weissbeerger.demo.configurations.UserAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TokenAuthService {
    @Autowired
    TokenHandler tokenHandler;
    @Autowired
    UserService userService;

    public final String AUTH_TOKEN="X-Auth-Token";
    public Optional<Authentication> getAuthentication(@NonNull HttpServletRequest servletRequest) {
        Optional<Authentication> userAuthentication = Optional.ofNullable(servletRequest.getHeader(AUTH_TOKEN))
                .flatMap(tokenHandler::extractUserId)
                .flatMap(userService::findById)
                .map(UserAuthentication::new);
        return userAuthentication;
    }
}
