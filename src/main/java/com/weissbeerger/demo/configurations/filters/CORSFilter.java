package com.weissbeerger.demo.configurations.filters;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter implements Filter {
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Methods", "OPTIONS, POST, GET, PUT, DELETE, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers",  "X-Auth-Token, Access-Control-Expose-Headers,Authorization, Cache-Control, Content-Type, Access-Control-Allow-Origin, Access-Control-Allow-Headers, Origin");
        response.setHeader("Access-Control-Allow-Headers", "Client-Id ,M-Token, Dis-Oauth-Token, Ggl-Oauth-Token,Fb-Oauth-Token,Vk-Oauth-Token, Git-Hub-Oauth-Token, X-Auth-Token, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(servletRequest, servletResponse);
        }
    }
    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}
