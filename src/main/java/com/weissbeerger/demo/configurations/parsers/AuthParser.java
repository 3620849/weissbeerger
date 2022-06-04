package com.weissbeerger.demo.configurations.parsers;
import com.weissbeerger.demo.configurations.UserAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
public interface AuthParser  {
    Authentication parseRequest(String searcHeader, UserAuthentication authentication, HttpServletRequest request) ;
}
