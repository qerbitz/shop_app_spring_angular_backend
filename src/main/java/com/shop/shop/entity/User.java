package com.shop.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(nullable = false, updatable = false)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "id")
    private int id;
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
   //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String email;
    //private String profileImageUrl;
    private Date lastLoginDate;
    private Date lastLoginDateDisplay;
    private Date joinDate;
    private String role; //ROLE_USER{ read, edit }, ROLE_ADMIN {delete}
    private String[] authorities;
    private boolean isActive;
    private boolean isNotLocked;


   // @OneToOne()
    //@JoinColumn(name = "id_adress", nullable = false)
   // private Address address;


}
