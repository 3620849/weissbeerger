package com.weissbeerger.demo.model;


import com.weissbeerger.demo.model.omdb.FilmDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Film {
    @Id
    @GeneratedValue
    private int id;
    private boolean sorceOmdb=true;
    @OneToOne(cascade = {CascadeType.MERGE})
    FilmDto film;

    public Film(FilmDto filmDto) {
        this.film = filmDto;
    }
}
