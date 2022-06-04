package com.weissbeerger.demo.configurations.providers;

import com.weissbeerger.demo.configurations.UserAuthentication;
import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.model.UserData;
import com.weissbeerger.demo.services.TokenHandler;
import com.weissbeerger.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserService userService;
    @Autowired
    TokenHandler tokenHandlerService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAuthentication auth = (UserAuthentication)authentication;
        if (auth.getAccessToken()==null || auth.getAccessToken().isBlank()){
            throw new BadCredentialsException("token is null");
        }
        User user = null;
        try{
             user = tokenHandlerService.
                    extractUserId(auth.getAccessToken()).
                    map(userService::findById).get().get();
             if(user==null){
                 throw new BadCredentialsException("wrong token");
             }
        }catch (Exception e){
            throw new BadCredentialsException("wrong token");
        }
        auth.setAuthenticated(true);
        ((UserAuthentication)authentication).setUser(user);
        return  auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
