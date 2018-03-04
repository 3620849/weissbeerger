package com.weissbeerger.demo.controllers;

import com.weissbeerger.demo.model.Role;
import com.weissbeerger.demo.model.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static java.util.stream.Collectors.joining;

@Controller
public class IndexPageController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage(Model model) {
        LOGGER.debug("URL / getAuthToken method: getAuthToken");
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        try{
            user = (User) authentication.getPrincipal();
        }catch (ClassCastException e){
            if (authentication.getPrincipal()=="anonymousUser"){
                user=new User();
                user.setId(-1);
                user.setUsername("anonymousUser");
                user.grantRole(Role.ROLE_ANONYMOUS);
            }
        }
        LOGGER.debug("URL / getAuthToken method: user defined as :" +user.getUsername()+" id: "+user.getId());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("roles",user.getAuthorities().stream().map(s -> s.getAuthority()).collect(joining(",")));
        return "index";
    }
}