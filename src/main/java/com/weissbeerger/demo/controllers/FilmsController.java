package com.weissbeerger.demo.controllers;

import com.weissbeerger.demo.model.Film;
import com.weissbeerger.demo.model.omdb.FilmShortDto;
import com.weissbeerger.demo.services.FilmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FilmsController {
    @Autowired
    FilmsService filmsService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    //localhost:8080/api/searchFilms?name=bat
    @RequestMapping(value = "/searchFilms" , params = {"name"} ,method = RequestMethod.GET)
    public ResponseEntity<FilmShortDto> searchFilmsList(@RequestParam("name")String name){
        LOGGER.debug("/searchFilms ");
        return new ResponseEntity<>(filmsService.getSearchFilms(name), HttpStatus.OK);
    }

    @RequestMapping(value = "/getFilmByImdbID" , params = {"imdbID"} ,method = RequestMethod.GET)
    public ResponseEntity<Film> getFilmByImdbID(@RequestParam("imdbID")String imdbID){
        LOGGER.debug("/getFilmByImdbID ");
        return new ResponseEntity<>(filmsService.getFilmByImdbID(imdbID), HttpStatus.OK);
    }

}
