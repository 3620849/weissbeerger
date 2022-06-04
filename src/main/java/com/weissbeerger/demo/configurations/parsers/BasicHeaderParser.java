package com.weissbeerger.demo.configurations.parsers;

import com.weissbeerger.demo.configurations.UserAuthentication;
import com.weissbeerger.demo.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;

import java.util.Base64;
import java.util.logging.Logger;

public class BasicHeaderParser implements AuthParser {

    @Override
    public UserAuthentication parseRequest(String searchHeader, UserAuthentication authentication, HttpServletRequest req) {
        String header = req.getHeader(searchHeader);
        if (header == null) {
            return authentication;
        }
        UserAuthentication value = null;
        if (header.matches("^Basic (?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})$")) {
            String[] basci_s = header.split("Basic ");
            if (basci_s.length > 1) {
                String userCredentials = null;
                userCredentials = new String(Base64.getDecoder().decode(basci_s[1].getBytes()));
                String[] userCredentialsArr = userCredentials.split(":");
                User userInfo = User.builder().username(userCredentialsArr[0]).password(userCredentialsArr[1]).build();
                authentication.setUser(userInfo);
            }
        }else {
            String error = "base64 error user password wrong format";
        }
        return authentication;
    }
}