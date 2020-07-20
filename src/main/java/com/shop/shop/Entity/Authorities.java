package com.shop.shop.Entity;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "authorities")
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_authority")
    private int id;

    @NotNull
    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "username")
    private User username;

    @NotNull
    @Column(name = "authority")
    private String authority;

    public Authorities() {
    }

    public Authorities(User username, String authority) {
    }


    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
