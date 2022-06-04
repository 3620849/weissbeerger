package com.weissbeerger.demo.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "User")
public  class User implements UserDetails {
    @GeneratedValue
    @Id
    private int Id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<UserAutority> authorities;
    private String password;
    @Column(unique = true)
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    //helper method to set roles for this user
    public void grantRole(Role role) {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        authorities.add(new UserAutority(this,role.name()));
    }
}
