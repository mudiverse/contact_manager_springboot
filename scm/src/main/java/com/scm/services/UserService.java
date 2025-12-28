package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;

public interface UserService {
    //some methods related to user
    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String userId);
    boolean isUserExistByEmail(String email);

    //list of all useres
    List<User> getAllUsers();

    User getUserByEmail(String email);
    //TODO: add more user related business logic methods if needed



    
}
