package com.weissbeerger.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAutority implements GrantedAuthority {
    @ManyToOne(fetch = FetchType.LAZY)
    @Id
    @JsonIgnore
    private User user;

    @Id
    private String authority;
    @Override
    public String getAuthority() {
        return authority;
    }

}
