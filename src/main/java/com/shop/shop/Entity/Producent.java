package com.shop.shop.Entity;

import javax.persistence.*;

@Entity
@Table(name="producent")
public class Producent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producent")
    private int id_producent;

    @Column(name = "name")
    private String name;

    public Producent() {
    }

    public int getId_producent() {
        return id_producent;
    }

    public void setId_producent(int id_producent) {
        this.id_producent = id_producent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
