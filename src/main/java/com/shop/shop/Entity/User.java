package com.shop.shop.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @NotNull
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "e_mail")
    private String e_mail;

    @Column(name = "enabled")
    private int enabled;

    @Column(name = "last_log")
    private Date last_log;

    @OneToOne
    @JoinColumn(name = "id_cart", nullable = false)
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "id_adress", nullable = false)
    private Adress adress;


}


