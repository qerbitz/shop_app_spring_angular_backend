package com.shop.shop.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}
