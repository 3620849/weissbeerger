package com.weissbeerger.demo.dao;

import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.model.UserData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public class UserDao {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public UserData getUserDataByUserId(@NonNull int id){
        TypedQuery<UserData> query =  em.createQuery("SELECT ud FROM UserData ud WHERE ud.user ='"+id+"'",UserData.class);
        return query.getSingleResult();
    }
    @Transactional
    public User findUserByName(@NonNull String name){
    TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = '"+name+"'",User.class);
        User user = null;
        try {
            user = query.getSingleResult();
        }catch (NoResultException e){
           // throw new UsernameNotFoundException("USER NOT FOUND");
        }
    return user;

}

    public Optional<User> findById(int id) {
        return Optional.ofNullable(em.find(User.class,id));
    }

    @Transactional
    public void addUser(@NonNull User user) {
        em.persist(user);
    }

}
