package com.weissbeerger.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAutority implements GrantedAuthority {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Id
    @JsonIgnore
    private User user;

    @NotNull
    @Id
    private String authority;
    @Override
    public String getAuthority() {
        return authority;
    }

}
