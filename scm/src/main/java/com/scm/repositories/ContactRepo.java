package com.scm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contacts;
import com.scm.entities.User;

import java.util.List;


@Repository
public interface ContactRepo extends JpaRepository<Contacts, String> {

    //custom finder methond
    Page<Contacts> findByUser(User user,Pageable pageable);

    // custom query methods for searching contacts
    @Query("SELECT c FROM Contacts c WHERE c.user.id = :userId")

    List<Contacts> findByUserid(@Param("userId") String userId);

    //custom query methods for searching contacts by name email phone number
    Page<Contacts> findByNameContaining(String name,Pageable pageable);
    Page<Contacts> findByEmailContaining(String email,Pageable pageable);
    Page<Contacts> findByPhonenumberContaining(String phone,Pageable pageable);



}
