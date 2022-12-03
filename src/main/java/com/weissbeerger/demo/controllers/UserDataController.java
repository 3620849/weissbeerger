package com.weissbeerger.demo.controllers;

import com.weissbeerger.demo.dao.SearchRepository;
import com.weissbeerger.demo.dao.UserDao;
import com.weissbeerger.demo.dao.UserDataDao;
import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.model.UserData;
import com.weissbeerger.demo.model.omdb.Search;
import com.weissbeerger.demo.services.FilmsService;
import com.weissbeerger.demo.services.UserDataService;
import com.weissbeerger.demo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserDataController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDataController.class);
    @Autowired
    private UserDataService userDataService;
    @Autowired
    private UserService userService;
    @Autowired
    private FilmsService filmsService;
    @Autowired
    UserDao userDao;
    @Autowired
    UserDataDao userDataDao;
    @Autowired
    SearchRepository searchRepository;
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #id")
    @RequestMapping(value = "/getUserInfo/{id}", method = RequestMethod.GET)
    public User getUserInfo (@PathVariable("id") int id){
        LOGGER.debug("URL /getUserInfo/{id} method:getUserInfo attribute id is: "+id);
        return userService.findById(id).get();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #id")
    @RequestMapping(value = "/getUserData/{id}", method = RequestMethod.GET)
    public UserData getUserData (@PathVariable("id") int id){
        LOGGER.debug("URL /getUserData/{id} method:getUserData attribute id is: "+id);
        UserData userDataById = userDataService.getUserDataById(id);
        return userDataById;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #id")
    @RequestMapping(value = "/getLikedList/{id}", method = RequestMethod.GET)
    public List<Search> getLikedFilmsByUserId (@PathVariable("id") int id){
        LOGGER.debug("URL /getLikedList/{id} method:getCarList attribute id is: "+id);
        return filmsService.getLikedFilmsByUserId(id);
    }

    @RequestMapping(value = "/isFilmLiked/{imdbID}", method = RequestMethod.GET)
    public boolean isFilmLiked (@PathVariable("imdbID") String imdbID) {
        User user = getCurrentUser();
        if(user==null)return false;
        for(Search s: filmsService.getLikedFilmsByUserId(user.getId())){
            if(s.getImdbID().equals(imdbID)){
                return true;
            }
        };
        return false;
    };
    @RequestMapping(value = "/addToLiked", method = RequestMethod.POST)
    public void addFilmToLiked (@RequestBody Search film) {
        LOGGER.debug("URL /addFilmToLiked  " + film);

        if(film==null)return;
       Search shortFilmDb = searchRepository.findByImdbID(film.getImdbID());
        if(shortFilmDb==null){
            searchRepository.save(film);
            shortFilmDb = searchRepository.findByImdbID(film.getImdbID());
        }
        User user = getCurrentUser();
        if(user==null)return;
        UserData userData = userDao.getUserDataByUserId(user.getId());
        if(userData.getLikedFilms().contains(shortFilmDb)){
            userData.getLikedFilms().remove(shortFilmDb);
        }else {        userData.getLikedFilms().add(shortFilmDb);}
        userDataDao.updateUserData(userData);
    }

    public User getCurrentUser(){
        User user = null;
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{
            user = (User) authentication.getPrincipal();
            if(user==null )return null;
        }catch (ClassCastException e){
            if (authentication.getPrincipal()=="anonymousUser"){
                LOGGER.debug("anonymus user can't execute this method");
                return null;
            }
        }
        return user;
    }

}
