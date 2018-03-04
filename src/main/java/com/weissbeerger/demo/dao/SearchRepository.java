package com.weissbeerger.demo.dao;

import com.weissbeerger.demo.model.UserData;
import com.weissbeerger.demo.model.omdb.Search;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SearchRepository {
    @PersistenceContext
    EntityManager em;

    public Search findByImdbID(String imdbID) {
        try{
        return em.createQuery("select s from Search s where s.imdbID='"+imdbID+"'",Search.class).getSingleResult();
    }catch (NoResultException e){
        return null;
    }
    }

    @Transactional
    public void save(Search film) {
        em.persist(film);
    }

    public List<Search> getLikedFilmsByUserId(int id) {
        try{
             UserData  result  =  em.createQuery("select ud from UserData ud  where ud.user.Id="+id,UserData.class).getSingleResult();
            return result.getLikedFilms();
        }catch (NoResultException e){
            return null;
        }
    }
}
