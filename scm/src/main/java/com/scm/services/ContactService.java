package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.scm.entities.Contacts;

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

    //search contacts
    List<Contacts>searchContacts(String name, String email, String phoneNumber);
    
    //get contacets by user id
    List<Contacts>getContactsByUserId(String userId);

    Page<Contacts> getContactsByUser(com.scm.entities.User user,int page, int size,String sortBy, String sortDir);

}
