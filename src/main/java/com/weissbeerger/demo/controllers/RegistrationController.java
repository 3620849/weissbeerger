package com.weissbeerger.demo.controllers;

import com.weissbeerger.demo.dao.UserDao;
import com.weissbeerger.demo.exceptions.ErrorResponse;
import com.weissbeerger.demo.model.Role;
import com.weissbeerger.demo.model.User;
import com.weissbeerger.demo.services.TokenHandler;
import com.weissbeerger.demo.services.UserService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;


@RestController
public class RegistrationController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService userService;

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public ResponseEntity<User> getCurrentUser(){
        LOGGER.debug("/getCurrentUser : method getCurrentUser ");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        try{
            user = (User) authentication.getPrincipal();
            if(user.getUsername()==null ||  user.getId()==0){
                throw new Exception("anonymousUser");
            }
            user =  userService.findById(user.getId()).get();
            user.setPassword(null);
        }catch (Exception e){
                user=new User();
                user.setUsername("anonymousUser");
                user.grantRole(Role.ROLE_ANONYMOUS);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody @NonNull User user){
        LOGGER.debug("/register : method register , user: "+user.getUsername());
        if(!userService.isUserExist(user)) {
            userService.addNewUser(user);
        }else {
            LOGGER.info("SOME ONE TRY REGISTER USER WITH SAME NAME: "+user.getUsername());
        }
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> exceptionHandling (Exception e){
        ErrorResponse error = new ErrorResponse(e);
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());

    }
}
