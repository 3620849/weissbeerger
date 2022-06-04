package com.weissbeerger.demo.configurations.filters;


import com.weissbeerger.demo.configurations.UserAuthentication;
import com.weissbeerger.demo.configurations.parsers.BasicHeaderParser;
import com.weissbeerger.demo.configurations.parsers.ClientIdParser;
import com.weissbeerger.demo.configurations.parsers.TokenHeaderParser;
import com.weissbeerger.demo.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.io.IOException;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {
    public LoginFilter(RequestMatcher requiresAuthenticationRequestMatcher){
        super(requiresAuthenticationRequestMatcher);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserAuthentication auth = (UserAuthentication) authResult;
        SecurityContextHolder.getContext().setAuthentication(auth);
        response.setHeader("X-Auth-Token",auth.getAccessToken());
        chain.doFilter(request,response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        UserAuthentication auth = new UserAuthentication(new User());
        new ClientIdParser().parseRequest("Client-id",auth,req);
        new TokenHeaderParser().parseRequest("X-Auth-Token",auth,req);
        new BasicHeaderParser().parseRequest("Authorization",auth,req);
        return this.getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
