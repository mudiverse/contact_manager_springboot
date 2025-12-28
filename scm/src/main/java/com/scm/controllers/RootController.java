package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.entities.User;
import com.scm.helpers.Helper;
import com.scm.services.UserService;

@ControllerAdvice
public class RootController {
    @Autowired
    private UserService userService;
     private Logger logger = LoggerFactory.getLogger(UserController.class);


    //agar user logged in hoga to uska data har page pe chahiye hoga else null aayega
    @ModelAttribute
    public void addLoggedInUserInfo(Model model, Authentication authentication) {
        if(authentication==null){
            return;
        }

        try {
            String name = Helper.getEmailOfLoggedInUser(authentication);
            logger.info("User Profile page : " + name);

            //now database se user ka data fetch kar ke profile page me show karna hai
            User user = userService.getUserByEmail(name);
            
            if(user != null) {
                System.out.println("User Profile page");
                System.out.println(user.getEmail());
                System.out.println(user.getName());
                model.addAttribute("loggedInUser", user);
            } else {
                logger.warn("User not found in database for email: " + name);
            }
        } catch (Exception e) {
            logger.error("Error fetching user info: " + e.getMessage());
        }
    }


}
