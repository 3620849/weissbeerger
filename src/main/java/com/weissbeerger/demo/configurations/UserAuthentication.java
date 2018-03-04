package com.weissbeerger.demo.configurations;

import com.weissbeerger.demo.model.User;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {
    public final User user;
    public boolean auth = true;
    public UserAuthentication(@NonNull User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword() ;
    }

    @Override
    public Object getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return auth;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        auth= isAuthenticated;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
