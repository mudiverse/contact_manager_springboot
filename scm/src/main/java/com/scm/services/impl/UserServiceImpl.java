package com.scm.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.UserRepo;
import com.scm.services.UserService;

import jakarta.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public User saveUser(User user) {
        //generate unique userid/dynamically
        String uid = UUID.randomUUID().toString();
        user.setUserid(uid);

        //pasword encoding can be done here before saving
        
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        //fetch the user from db
       User user1 = userRepo.findById(user.getUserid()).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "));
         //update the fields
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setAbout(user.getAbout());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setProfilePic(user.getProfilePic());
        user1.setEnabled(user.isEnabled());
        user1.setEmailVerified(user.isEmailVerified());
        user1.setPhoneVerified(user.isPhoneVerified());
        user1.setProvider(user.getProvider());
        user1.setProviderId(user.getProviderId());

        //save the user
        User updatedUser = userRepo.save(user1);
        return Optional.ofNullable(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        //fetch the user from db
       User user1 = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "));
       userRepo.delete(user1);
    }

    @Override
    public boolean isUserExist(String userId) {
        User user2 = userRepo.findById(userId).orElse(null);
        return user2!=null ? true : false;
    }

    @Override
    public boolean isUserExistByEmail(String email) {
      User s=  userRepo.findByEmail(email).orElse(null);
      return s!=null ? true : false;

    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    
}
