package com.shop.shop.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int id_product;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_category", nullable = false)
    private Category id_category;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_producent", nullable = false)
    private Producent producent;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "size_age", nullable = false)
    private Size_Age size_age;

    @Column(name = "image")
    private String image;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "season")
    private String season;

    public Product(int id_product, String name, String gender, Double price, Category id_category, Producent producent, Size_Age size_age, String image, int quantity, String season) {
        this.id_product = id_product;
        this.name = name;
        this.gender = gender;
        this.price = price;
        this.id_category = id_category;
        this.producent = producent;
        this.size_age = size_age;
        this.image = image;
        this.quantity = quantity;
        this.season = season;
    }

    public Product() {
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String description) {
        this.gender = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getId_category() {
        return id_category;
    }

    public void setId_category(Category id_category) {
        this.id_category = id_category;
    }

    public Size_Age getSize_age() {
        return size_age;
    }

    public void setSize_age(Size_Age size_age) {
        this.size_age = size_age;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Producent getProducent() {
        return producent;
    }

    public void setProducent(Producent producent) {
        this.producent = producent;
    }
}
