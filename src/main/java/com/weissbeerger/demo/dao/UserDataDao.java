package com.weissbeerger.demo.dao;

import com.weissbeerger.demo.model.UserData;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class UserDataDao {
    @PersistenceContext
    EntityManager em;

    @Transactional
    public void addUserData(UserData userData){
        em.persist(userData);
    }
    @Transactional
    public void updateUserData(UserData userData){
        em.merge(userData);
    }
}
