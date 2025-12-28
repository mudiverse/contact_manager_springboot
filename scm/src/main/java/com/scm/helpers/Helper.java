package com.scm.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {


    public static String getEmailOfLoggedInUser(Authentication authentication){

        //agar email ans pass se loging kiya h to  : email return hoga
        if(authentication instanceof OAuth2AuthenticationToken){

            var authToken = (OAuth2AuthenticationToken) authentication;
            var clientid = authToken.getAuthorizedClientRegistrationId();
            var oauth2User = (OAuth2User)authentication.getPrincipal();
            String usrname = ""; // by default empty
            if(clientid.equalsIgnoreCase("google")){

                System.out.println("Getting email from google");
                usrname = oauth2User.getAttribute("email").toString();


            }
            else if(clientid.equalsIgnoreCase("github")){
                System.out.println("Getting email from github");
                 usrname= oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("email").toString()
                    : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }
            return usrname;
        }
        
        else{
            System.out.println("Getting email  form login");
            return authentication.getName();
        }
    }
}
