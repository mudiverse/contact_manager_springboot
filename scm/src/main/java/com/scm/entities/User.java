package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity(name ="user")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    
    @Id  //primary key
    private String userid;

    @Column(name = "user_name",nullable = false)
    private String name;
    
    @Column(unique = true,nullable = false)
    private String email;
    
    private String password;
    
    @Column(length = 5000)
    private String about;

    @Column(length = 1000)
    private String profilePic;

    private String phoneNumber;
    //information
    private boolean enabled=false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;

    //SELf , google, github,Faceook -> kis provider
    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF; 
    private String providerId; 

    //add more fields if needed


    //1 to many mapping with contacts
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private List<Contacts> contacts= new ArrayList<>();    


}
