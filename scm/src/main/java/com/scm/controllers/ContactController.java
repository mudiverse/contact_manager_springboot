package com.scm.controllers;

import java.util.UUID;
import java.util.logging.Logger;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contacts;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/user/contacts")  // /user/contacts is a route namespace
public class ContactController {
    
    @Autowired
    private ContactService contactService;
    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    Logger logger = Logger.getLogger(ContactController.class.getName());

    
    @RequestMapping("/add")
    //add contacts handler method
    public String addContactView(Model model){
        
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm); // this will be used to bind form data , it sends empty object to the form

        //tetsing 
        // contactForm.setFavourite(true);
        return "user/add_contact";     // but here user/add_contact is the view name or html page name
    }

    //handler of form 

    @RequestMapping(value= "/add" , method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm , BindingResult bindingResult, Authentication auth,
        HttpSession session, Model model){
        //process the form data and save the contact
        // form object to contact entity object
        // to get user we need Authentication object 

        // Validadtion  logic here pending
        if(bindingResult.hasErrors()){
            session.setAttribute("message",Message.builder().content("Please fill all the fields correctly!").type(MessageType.red).build());
            return "user/add_contact";
        }

        String  username = Helper.getEmailOfLoggedInUser(auth);

        User user = userService.getUserByEmail(username);
        
        //processing the contact picture here

        //file upload krne ka code 
        String filename = UUID.randomUUID().toString();
        String fileURL = imageService.uploadImage(contactForm.getContactImage(),filename);  
        

        Contacts contact = new Contacts();
        //form se data lekr entity me set krna
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhonenumber(contactForm.getPhonenumber());
        contact.setFavourite(contactForm.isFavourite());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user); // indicates which user this contact belongs to 
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        //set the contact picture
        contact.setPicture(fileURL); //url will be stored in db
        contact.setCloudinaryImagePublicId(filename);

        contactService.saveContact(contact);
        session.setAttribute("message",Message.builder().content("Contacts Added Successfully!").type(MessageType.green).build());
        return "redirect:/user/contacts/add";
    }

    @RequestMapping
    public String viewContacts(
        @RequestParam(value="page",defaultValue = "0") int page , 
        @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size ,
        @RequestParam(value = "sortBy",defaultValue = "name") String sortBy ,
        @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir,
        Model model, Authentication authentication){
        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);
        Page<Contacts> pageContacts = contactService.getContactsByUser(user,page,size,sortBy,sortDir);
        

        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        //the above code gets all contacts of the logged in user and adds them to the model
        // adding to model means we can access it in the view (html page)
        return "user/contacts";
    }

    //searchbar handler method
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchHandler(
        @ModelAttribute ContactSearchForm contactSearchForm,
    @RequestParam(value="page",defaultValue = "0") int page , 
        @RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size ,
        @RequestParam(value = "sortBy",defaultValue = "name") String sortBy ,
        @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir,
    Model model ,Authentication authentication){
        
        logger.info("Search field: " + contactSearchForm.getField());
        logger.info("Search keyword: " + contactSearchForm.getValue());

        var  user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contacts> pageContacts =null;
        if(contactSearchForm.getField().equalsIgnoreCase("name")){
           pageContacts = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, sortDir,user);
        }
        else if(contactSearchForm.getField().equalsIgnoreCase("email")){
            pageContacts = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, sortDir,user);
        }else if(contactSearchForm.getField().equalsIgnoreCase("phone")){
            pageContacts = contactService.searchByPhone(contactSearchForm.getValue(), size, page, sortBy, sortDir,user);
        }
        
        logger.info("pageContacts: " + pageContacts);
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageSize",AppConstants.PAGE_SIZE);

        return "user/search";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId,HttpSession session){

        contactService.deleteContact(contactId);
        session.setAttribute("message", 
            Message.builder()
            .content("Contact Deleted Successfully!!")
            .type(MessageType.green)
            .build()
        );

        return "redirect:/user/contacts";
    }
}
