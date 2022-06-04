package com.weissbeerger.demo.configurations.parsers;
import com.weissbeerger.demo.configurations.UserAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
public class TokenHeaderParser implements AuthParser {
    @Override
    public Authentication parseRequest(String searchHeader, UserAuthentication auth, HttpServletRequest req){
        String header = req.getHeader(searchHeader);
        if (header == null) {
            return auth;
        }
        setToken ( auth, header);
        return auth;
    }
    public void setToken (UserAuthentication auth,String header){
        auth.setAccessToken(header);
    }
}
