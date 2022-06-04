package com.weissbeerger.demo.dao;

import com.weissbeerger.demo.model.Film;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public class FilmsRepository {
    @PersistenceContext
    EntityManager em;

    /*@Transactional
    public List<Film> getLikedFilmsByUserId (@NonNull int id){

        //return em.createQuery("SELECT ud.carInUse from UserData ud where ud.user ='"+id+"'",Film.class).getResultList();
        return null;
    }

    public List<Film> getAllFilms() {

        //return em.createQuery("SELECT c from Car c",Car.class).getResultList();
        return null;
    }*/

    public Film getFilmByImdbID(String imdbID) {

                try {
                    return  em.createQuery("SELECT f from Film f where f.film.imdbID ='" + imdbID + "'", Film.class).getSingleResult();
                }catch (Exception e){
                   return null;
                }
    }

    public void save(Film film) {
        em.merge(film);
    }

}
