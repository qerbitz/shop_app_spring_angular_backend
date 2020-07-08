package com.shop.shop.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user")
@Getter
@Setter
public class User {

    @Id
    @Column(name="id_user")
    private int id_user;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

   /* @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private String phone;
*/
    @Column(name = "e_mail")
    private String e_mail;

   // @OneToOne
    //@JoinColumn(name = "adress", nullable = false)
    //private Adress id_adress;


    public User() {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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
}
