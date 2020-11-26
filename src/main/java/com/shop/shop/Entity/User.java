package com.shop.shop.Entity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import javax.persistence.*;

@Entity
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


    public User(@NotNull String username, String password, String name, String surname, String phone, String e_mail, int enabled, Date last_log, Cart cart, Adress adress) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.e_mail = e_mail;
        this.enabled = enabled;
        this.last_log = last_log;
        this.cart = cart;
        this.adress = adress;
    }

    public User() {
    }

    public User(String username) {
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Adress getAdress() {
        return adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    public Date getLast_log() {
        return last_log;
    }

    public void setLast_log(Date last_log) {
        this.last_log = last_log;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", phone='" + phone + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", enabled=" + enabled +
                ", cart=" + cart +
                ", adress=" + adress +
                '}';
    }
}
