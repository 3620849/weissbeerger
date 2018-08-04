package com.weissbeerger.demo.configurations.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.weissbeerger.demo.configurations.UserAuthentication;
import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    UserService userService;

    public LoginFilter(RequestMatcher requiresAuthenticationRequestMatcher, AuthenticationManager authenticationManager,UserService userService) {
        super(requiresAuthenticationRequestMatcher);
        this.setAuthenticationManager(authenticationManager);
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        if (httpServletRequest.getMethod().equals("POST")) {
            try {
                user = mapper.readValue(httpServletRequest.getInputStream(), User.class);
            } catch (Exception e) {
                throw new BadCredentialsException("can't map user");
            }
            if (user.getUsername() == null && user.getPassword() == null) {
                throw new BadCredentialsException("user have empty credentials");
            }
            UserAuthentication authentication = new UserAuthentication(user);
            return this.getAuthenticationManager().authenticate(authentication);
        }
        throw new BadCredentialsException("");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = ((UserAuthentication)authResult).getAccessToken();
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setStatus(200);
        response.getWriter().write(token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
        //return fail
    }
}
