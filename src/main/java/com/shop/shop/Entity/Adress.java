package com.shop.shop.Entity;

import javax.persistence.*;

@Entity
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

    public Adress() {
    }

    public Adress(int id_adress, String place, String voivodeship, String street, String nr_house, String zip_code) {
        this.id_adress = id_adress;
        this.place = place;
        this.voivodeship = voivodeship;
        this.street = street;
        this.nr_house = nr_house;
        this.zip_code = zip_code;
    }

    public int getId_adress() {
        return id_adress;
    }

    public void setId_adress(int id_adress) {
        this.id_adress = id_adress;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getVoivodeship() {
        return voivodeship;
    }

    public void setVoivodeship(String voivodeship) {
        this.voivodeship = voivodeship;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNr_house() {
        return nr_house;
    }

    public void setNr_house(String nr_house) {
        this.nr_house = nr_house;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    @Override
    public String toString() {
        return "Adress{" +
                "id_adress=" + id_adress +
                ", place='" + place + '\'' +
                ", voivodeship='" + voivodeship + '\'' +
                ", street='" + street + '\'' +
                ", nr_house='" + nr_house + '\'' +
                ", zip_code='" + zip_code + '\'' +
                '}';
    }
}
