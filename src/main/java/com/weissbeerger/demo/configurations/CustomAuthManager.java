package com.weissbeerger.demo.configurations;

import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.services.TokenHandler;
import com.weissbeerger.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class CustomAuthManager implements AuthenticationManager {
    @Autowired
    UserService userService;
    @Autowired
    TokenHandler tokenHandler;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = (User) authentication.getPrincipal();
        UserAuthentication userAuthentication = (UserAuthentication)authentication;
        if (user.getUsername() == null && user.getPassword() == null) {
            throw new BadCredentialsException("null username or password");
        }
        User dbUser;
        dbUser = userService.findUserByName(user).map(u->u).orElseThrow(() -> new BadCredentialsException("no such user in db"));
        if (tokenHandler.checkMatchesPasswords(dbUser, user)) {
            userAuthentication.setAccessToken(tokenHandler.generateTokenId(dbUser.getId(), LocalDateTime.now().plusHours(1)));
        }else {
            throw new BadCredentialsException("AuthenticationManager find wrong password");
        }
        return userAuthentication;
    }
}
