package com.shop.shop.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@Table(name="adress")
@Getter
@Setter
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

    @Column(name="kod_pocztowy")
    private String zip_code;
}
