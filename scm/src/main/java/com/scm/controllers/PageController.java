package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page Handler");
        model.addAttribute("name", "New Poject");
        model.addAttribute("YoutubeChannel", "ABC YT");
        model.addAttribute("gitHub", "ABC GIT");
         return "Home";
     }

    @RequestMapping("/about")
    public String about() {
        System.out.println("About page");
         return "ABOUT";
    }
    @RequestMapping("/services")
    public String services() {
        System.out.println("Services page");
         return "Services";
    }
   
    @GetMapping("/contact")
    public String contact() {
        System.out.println("Contact page");
         return "Contact";
    }

    //login page rout
    @GetMapping("/login")
    public String login() {
        System.out.println("Login page");
         return "Login";
    }
    
    //register page rout
    @GetMapping("/register")
    public String register() {
        System.out.println("Register page");
         return "Register";
    }
}
