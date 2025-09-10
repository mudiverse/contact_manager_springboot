package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/user")
public class UserController {

    //dashboard page
    @RequestMapping(value = "/dashboard", method=RequestMethod.GET) 
    public String userDashboard(){
        System.out.println("User Dashboard page");
        return "user/dashboard";
    }

        @RequestMapping(value = "/profile", method=RequestMethod.GET) 
    public String userProfile(){
        System.out.println("User Profile page");
        return "user/profile";
    }


    //add contact page

    //view contacts page

    //edit contact page

    //user search contact page

    //delete contact page


}
