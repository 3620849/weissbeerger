package com.weissbeerger.demo.dao;

import com.weissbeerger.demo.model.omdb.FilmDto;
import com.weissbeerger.demo.model.omdb.FilmShortDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class FilmDao {
    final String key ;
    RestTemplate restTemplate;

    FilmDao(@Value("${omdb.key}")String key, RestTemplateBuilder restTemplateBuilder ){
        this.restTemplate=restTemplateBuilder.build();
        this.key = key;
    }

    public FilmDto getFilmByImdbID(String imdbID) {
        String url = "http://www.omdbapi.com/?apikey="+key+"&i="+imdbID;
        FilmDto response = restTemplate.getForObject(url, FilmDto.class);
        if(response==null){return null;}
        return response;

    }
}
