package com.weissbeerger.demo.services;


import com.weissbeerger.demo.dao.UserDao;
import com.weissbeerger.demo.dao.UserDataDao;
import com.weissbeerger.demo.model.Role;
import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.model.UserData;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserDataDao userDataDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        //here   method wich retrieve user from db and mathc it
        User userByName = userDao.findUserByName(user);
        if(userByName==null){throw new UsernameNotFoundException("NO SO SUCH USER");}
        return userByName;
    }

    public  Optional<User> findById(int id) {
       return userDao.findById(id);
    }
    public  Optional<User> findUserByName(User  user) {
        return Optional.ofNullable(userDao.findUserByName(user.getUsername()));
    }

    public void addNewUser(@NonNull User user) {
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);
        user.grantRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
        UserData userData = new UserData();
        userData.setUser(userDao.findUserByName(user.getUsername()));
        userDataDao.addUserData(userData);
    }

    public boolean isUserExist(User user) {
        user = userDao.findUserByName(user.getUsername());
        return user==null?false:true;
    }
}
