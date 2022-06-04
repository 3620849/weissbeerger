package com.weissbeerger.demo.configurations.providers;


import com.weissbeerger.demo.configurations.UserAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AnonymousProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthentication auth = (UserAuthentication) authentication;
        if (auth.getClientId() == null || auth.getClientId().isBlank()) {
            throw new BadCredentialsException("this user not even anonymous");
        }
        auth.setAuth(true);
        return auth;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
