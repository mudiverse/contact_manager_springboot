package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entities.Contacts;
import com.scm.entities.User;

public interface ContactService {

    //alll the methods related to contact will be declared here

    Contacts saveContact(Contacts contact);

    //update contact
    Contacts update(Contacts contact);

    //get contacts list
    List<Contacts>getAllContacts();

    //get contact by id
    Contacts getContactById(String id);

    //delete contact
    void deleteContact(String id);

    //search contacts by name, email or phone number
    Page<Contacts>searchByName(String name, int size, int page, String sortBy, String sortDir,User User);
    Page<Contacts>searchByEmail(String email, int size, int page, String sortBy, String sortDir,User User);
    Page<Contacts>searchByPhone(String phone, int size, int page, String sortBy, String sortDir,User User);
    
    //get contacets by user id
    List<Contacts>getContactsByUserId(String userId);

    Page<Contacts> getContactsByUser(com.scm.entities.User user,int page, int size,String sortBy, String sortDir);

}
