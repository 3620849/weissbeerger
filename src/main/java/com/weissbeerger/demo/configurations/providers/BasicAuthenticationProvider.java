package com.weissbeerger.demo.configurations.providers;

import com.weissbeerger.demo.configurations.UserAuthentication;
import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.model.UserData;
import com.weissbeerger.demo.services.TokenHandler;
import com.weissbeerger.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BasicAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserService userService;
    @Autowired
    TokenHandler tokenHandlerService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthentication auth = (UserAuthentication) authentication;
        User userFromRequest = (User) auth.getDetails();
        if (userFromRequest == null || userFromRequest.getUsername() == null || userFromRequest.getPassword() == null) {
            throw new BadCredentialsException("fuck off");
        }
        User userAtDb = (User) userService.loadUserByUsername(userFromRequest.getUsername());

        if (userAtDb.getUsername().equals(userFromRequest.getUsername())) {
            if (passwordEncoder.matches(userFromRequest.getPassword(), userAtDb.getPassword())) {
                userFromRequest.setAuthorities(userFromRequest.getAuthorities());
                auth.setAccessToken(tokenHandlerService.generateTokenId(userAtDb.getId(), LocalDateTime.now().plusHours(24)));
                auth.setUser(userAtDb);
                auth.setAuthenticated(true);
            } else {
                throw new BadCredentialsException("fuck off");
            }

        } else {
            throw new BadCredentialsException("fuck off");
        }
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
