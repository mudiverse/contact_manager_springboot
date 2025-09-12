package com.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.entities.User;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, String> {
    //extra db related operations can be defined here

    //custom query methods can be defined here

    //custom finder methods can be defined here
    Optional<User>findByEmail(String email);
}
