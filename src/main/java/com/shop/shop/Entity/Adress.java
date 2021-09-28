package com.shop.shop.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="adress")
public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_adress")
    private int id_adress;

    @Column(name="place")
    private String place;

    @Column(name="voivodeship")
    private String voivodeship;

    @Column(name="street")
    private String street;

    @Column(name="nr_house")
    private String nr_house;

    @Column(name="zip_code")
    private String zip_code;

}
