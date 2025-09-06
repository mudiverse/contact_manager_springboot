package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;




@Controller
public class PageController {

    @RequestMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page Handler");
        model.addAttribute("name", "New Poject");
        model.addAttribute("YoutubeChannel", "ABC YT");
        model.addAttribute("gitHub", "ABC GIT");
         return "home";
     }

    @RequestMapping("/about")
    public String about() {
        System.out.println("About page");
         return "about";
    }
    @RequestMapping("/services")
    public String services() {
        System.out.println("Services page");
         return "services";
    }
   
    @GetMapping("/contact")
    public String contact() {
        System.out.println("Contact page");
         return "contacts";
    }

    //login page rout
    @GetMapping("/login")
    public String login() {
        System.out.println("Login page");
         return "login";
    }
    
    //register page rout
    @GetMapping("/register")
    public String register() {
        System.out.println("Register page");
         return "register";
    }
}
