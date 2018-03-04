package com.weissbeerger.demo.services;

import com.weissbeerger.demo.dao.FilmDao;
import com.weissbeerger.demo.dao.FilmShortDao;
import com.weissbeerger.demo.dao.FilmsRepository;
import com.weissbeerger.demo.dao.SearchRepository;
import com.weissbeerger.demo.model.Film;
import com.weissbeerger.demo.model.omdb.FilmDto;
import com.weissbeerger.demo.model.omdb.FilmShortDto;
import com.weissbeerger.demo.model.omdb.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


@Service
public class FilmsService {
    @Autowired
    FilmsRepository filmsRepository;
    @Autowired
    FilmShortDao filmShortDao;
    @Autowired
    FilmDao filmDao;
    @Autowired
    SearchRepository searchRepository;
    public List<Film>getAllFilmsList (){
        return null;}

    public List<Search>getLikedFilmsByUserId (int id){
        return searchRepository.getLikedFilmsByUserId(id);
    }

    public FilmShortDto getSearchFilms(String name) {
        return filmShortDao.searchFilm(name);

    }
    @Transactional
    public Film getFilmByImdbID(String imdbID) {
        Film film = null;
        //search in DB
        film =  filmsRepository.getFilmByImdbID(imdbID);
        if(film!=null){
            if(film.isSorceOmdb()){
                film.setSorceOmdb(false);
                filmsRepository.save(film);
            }
            return film;
        }else {
            //if in DB empty search in omdb
            FilmDto filmDto = filmDao.getFilmByImdbID(imdbID);
            if(filmDto==null){
                return null;
            }
            film = new Film(filmDto);
            filmsRepository.save(film);
            return film;
        }
    }
}
