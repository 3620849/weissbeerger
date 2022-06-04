package com.weissbeerger.demo.configurations.parsers;

import com.weissbeerger.demo.configurations.UserAuthentication;

public class ClientIdParser extends TokenHeaderParser {
    @Override
    public void setToken(UserAuthentication auth, String header) {
        auth.setClientId(header);
    }
}