package com.weissbeerger.demo.model;

import com.weissbeerger.demo.model.omdb.Search;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user_data")
public class UserData {
    @GeneratedValue
    @Id
    private int id;
    private String userName;
    private String userFamily;
    private String userPhone;
    @OneToOne
    @JoinColumn(unique=true)
    private User user;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="user_data_likedfilms",
            joinColumns=@JoinColumn(name="UserData_id"),
            inverseJoinColumns=@JoinColumn(name="likedFilms_id"))
    private List<Search> likedFilms;
}
