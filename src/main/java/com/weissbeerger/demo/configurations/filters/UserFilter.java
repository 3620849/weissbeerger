package com.weissbeerger.demo.configurations.filters;

import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.services.TokenAuthService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*
 * filter wich intercapts all request and change credentials
 */

public class UserFilter extends GenericFilterBean {
    private final TokenAuthService tokenAuthService;

    public UserFilter(TokenAuthService tokenAuthService) {
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = tokenAuthService.getAuthentication((HttpServletRequest) servletRequest).orElse(null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
