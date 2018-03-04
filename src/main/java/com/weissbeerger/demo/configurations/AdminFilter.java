package com.weissbeerger.demo.configurations;

import com.weissbeerger.demo.services.TokenAuthService;
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

public class AdminFilter extends GenericFilterBean {
    private final TokenAuthService tokenAuthService;

    public AdminFilter(TokenAuthService tokenAuthService) {
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(
                tokenAuthService.getAuthentication((HttpServletRequest)servletRequest).orElse(null)
        );
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
