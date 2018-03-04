package com.weissbeerger.demo.services;

import com.weissbeerger.demo.dao.UserDao;
import com.weissbeerger.demo.model.UserData;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserDataService {
    @Autowired
    private UserDao userDao;
    public UserData getUserDataById(@NonNull int id){
        return userDao.getUserDataByUserId(id);
    }
}
