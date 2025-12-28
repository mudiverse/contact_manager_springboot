package com.scm.controllers;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {
    @Autowired
    private UserService userService;

    

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
    public String register(Model model) {
        UserForm userForm = new UserForm();
        // userForm.setName("John Doe");  //defoult or example value
        // userForm.setAbout("Hey my name is John Doe. I am a...");

        model.addAttribute("userForm",userForm);
         return "register";
    }

    //processing register/signup forms
   
    
    @RequestMapping(value = "/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult bindingResult ,HttpSession session) { // apne app user form ka object ban jayega as same values hai
        System.out.println("Processing Register form");
        //fetch th form data
        System.out.println(userForm);
        //validate the form data
        //TODO:validate
        
        if(bindingResult.hasErrors()){
            return "register"; //agar error hai to wapis register page pr chala jayega
        }


        //save to database -> using user service(business logic)
        //user form ka use krke user banaya hai using builder pattern
        // User user = User.builder()
        // .name(userForm.getName())
        // .about(userForm.getAbout())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .phoneNumber(userForm.getPhoneNumber())
        // .enabled(false) //by default false
        // .profilePic("call.png") //default pic
        // .build();
        User user = new User();
        user.setName(userForm.getName());
        user.setAbout(userForm.getAbout()); 
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false); //by default false
        user.setProfilePic("call.png"); //default pic
        
 
        userService.saveUser(user);
        System.out.println("user saved to db");

        //message success
        Message message = Message.builder().content("Regisration Successful !!").type(MessageType.green).build(); //imp
        session.setAttribute("message", message);
 
        //redirect to register page
        return "redirect:/register";
    }

}
