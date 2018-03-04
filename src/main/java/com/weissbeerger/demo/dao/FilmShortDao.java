package com.weissbeerger.demo.dao;

import com.weissbeerger.demo.model.omdb.FilmShortDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.PermitAll;

@Component
public class FilmShortDao {
    final String key ;
    RestTemplate restTemplate;
    FilmShortDao(@Value("${omdb.key}")String key, RestTemplateBuilder restTemplateBuilder ){
        this.restTemplate=restTemplateBuilder.build();
        this.key = key;
    }

    public FilmShortDto searchFilm(String name){
        String url = "http://www.omdbapi.com/?apikey="+key+"&s="+name;
        FilmShortDto response = restTemplate.getForObject(url, FilmShortDto.class);
        if(response.getSearch()==null){return null;}
        return response;
    }
}
