package com.weissbeerger.demo.dao;

import com.weissbeerger.demo.model.UserData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


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
