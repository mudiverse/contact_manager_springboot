package com.scm.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {


    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    //dashboard page
    @RequestMapping(value = "/dashboard") 
    public String userDashboard(){
        System.out.println("User Dashboard page");
        return "user/dashboard";
    }

    @RequestMapping(value = "/profile") 
    public String userProfile(Model model,Authentication authentication){
        return "user/profile";
    }


    //add contact page

    //view contacts page

    //edit contact page

    //user search contact page

    //delete contact page


}
