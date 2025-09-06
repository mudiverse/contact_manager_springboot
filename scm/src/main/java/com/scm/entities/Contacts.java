package com.scm.entities;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contacts {

    @Id //primary key
    private String id;
    private String name;
    private String email;
    private String phonenumber;
    private String picture;

    @Column(length=1000)
    private String address;
    private String description;
    //favourite
    private boolean favourite = false;
    private String websiteLink;
    private String linkedInLink;


    //mapping
    @ManyToOne
    private User user;
    //1 to many mapping with social links
    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();

}
